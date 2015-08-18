package com.baremind;

import com.baremind.algorithm.Securities;
import com.baremind.data.Account;
import com.baremind.data.Resource;
import com.baremind.data.UploadLog;
import com.baremind.data.UploadMeta;
import com.baremind.utils.Hex;
import com.baremind.utils.IdGenerator;
import com.baremind.utils.JPAEntry;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("resources")
public class Resources {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Resource post(@CookieParam("sessionId") String sessionId, Resource resource) {
        //find account from accounts-table by sessionId
        //and last operation time > now - 30min
        //update last operation time
        //store resource to resources-table
        return resource;
    }

    @GET
    public List<Resource> get() {
        return new ArrayList<Resource>();
    }

    @GET
    @Path("{id}")
    public Resource getById(@CookieParam("sessionId") String sessionId, @PathParam("id") Long id) {
        return new Resource();
    }

    @POST
    @Consumes({"application/pdf", "application/msword"})
    public Resource postContent() {
        return new Resource();
    }

    public Resource getContent() {
        return new Resource();
    }

    private static class UploadPostProcessor extends Thread {
        public UploadPostProcessor(UploadLog uploadLog, EntityManager em) {
            this.uploadLog = uploadLog;
            this.em = em;
        }
        UploadLog uploadLog;
        EntityManager em;

        @Override
        public void run() {
            //thread:

            // set state to processing
            em.getTransaction().begin();
            uploadLog.setState(1);
            em.persist(uploadLog);
            em.getTransaction().commit();

            File file = new File(uploadLog.getFilePath());
            File fileZip = new File("D:\\var\\zipFiles\\");
            Securities.zip.uncompress(file ,fileZip);
            Gson json = new Gson();
            Reader reader = null;
            try {
                reader = new InputStreamReader(new FileInputStream("D:\\var\\zipFiles\\"+"_meta.json"));
                UploadMeta meta = json.fromJson(reader, UploadMeta.class);
                Resource resource = new Resource(meta);
                em.getTransaction().begin();
                CopyOption[] options = new CopyOption[]{
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.COPY_ATTRIBUTES
                };
                Files.move(Paths.get("D:\\var\\zipFiles\\"+resource.getFilePath()), Paths.get("D:\\var\\zipFilesSave\\"+resource.getFilePath()),options);
                Files.move(Paths.get("D:\\var\\zipFiles\\"+resource.getCover()), Paths.get("D:\\var\\zipFilesSave\\"+resource.getCover()),options);

                InputStream inputStream = new FileInputStream("D:\\var\\zipFilesSave\\"+resource.getFilePath());
                String d = Hex.bytesToHex(Securities.digestor.digest(inputStream));
                resource.setDigest(d);

                em.persist(resource);
                em.getTransaction().commit();

                em.getTransaction().begin();
                uploadLog.setState(2);
                em.persist(uploadLog);
                em.getTransaction().commit();

            } catch (IOException e) {
                e.printStackTrace();
            }
            // insert to resource-table
            // move cover & content file to spec folder
            // calc content file digest
            // crypto content file -- options
            // set state to processed

            // copyright insert
            // right transfer insert
            // resource transfer insert
        }

    }

    @POST
    @Consumes({"application/octet-stream", "application/zip", "application/x-compressed"}) //MediaType.APPLICATION_OCTET_STREAM_TYPE
    @Produces(MediaType.APPLICATION_JSON)
    public Response postZip(@Context HttpServletRequest request, @CookieParam("sessionId") String sessionId) {
        Response result = null;
        EntityManager em = JPAEntry.getEntityManager();
        String sql = "SELECT a FROM Account a WHERE a.sessionId=:sessionId";
        Account accounts = em.createQuery(sql,Account.class).setParameter("sessionId", sessionId).getSingleResult();
//        Date date = new Date();
//        String time = new Date().toString();
//        Date date = new SimpleDateFormat("yyyy/mm/dd hh:MM:ss").parse(time);
//
//        Calendar cal= Calendar.getInstance();
//        cal.setTime(date);
//
//        cal.add(Calendar.HOUR, -1);
//        Date yesterday =cal.getTime();
        try {
            int length = request.getContentLength();
            byte[] buffer = new byte[length];
            ServletInputStream servletInputStream = request.getInputStream();
            servletInputStream.read(buffer);
            File zipFile = new File("D:\\var\\files\\"+IdGenerator.getNewId()+".zip");
            FileOutputStream w = new FileOutputStream(zipFile);
            w.write(buffer);
            w.close();

            //insert record to table[id, time, uploader_id, file_path, status: 0(received) 1(processing) 2(processed)]
            em.getTransaction().begin();
            UploadLog uploadLog = new UploadLog();
            uploadLog.setId(IdGenerator.getNewId());
            uploadLog.setTime(new Date());
            uploadLog.setFilePath(zipFile.getPath());
            uploadLog.setState(0);
            em.persist(uploadLog);
            em.getTransaction().commit();

            //{"metaInfo":{"message":"upload success","code":0},"data":"15"}
            result = Response.ok("{\"metaInfo\":{\"message\":\"upload success\",\"code\":0},\"data\":\"" + uploadLog.getId() + "\"}").build(); //new Gson().toJson(uploadLog)
            new UploadPostProcessor(uploadLog, em).start();
        } catch (IOException e) {
            e.printStackTrace();
            result = Response.status(500).build();
        }
        return result;
    }

    @GET
    @Path("{id}")
    public Response queryState(@PathParam("id") Long id) {
        Response result = null;
        //{"metaInfo":{"message":"鏌ヨ鎴愬姛","code":0},"data":9}
        EntityManager em = JPAEntry.getEntityManager();
        String sql = "SELECT l FROM UploadLog l WHERE l.id=:id";
        UploadLog uploadLog = em.createQuery(sql, UploadLog.class).setParameter("id", id).getSingleResult();
        return result;
    }

//    @POST
//    @Consumes("multipart/form-data")
//    public Response postZipByFormData(MultipartFormDataInput input, MultivaluedMap<String, String> header, @MultipartForm FileUploadForm input) {
//        //
//    }

    private static final String FILE_PATH = "c:\\file.log";

    @GET
    @Path("{id}")
    @Produces({"application/pdf", "application/msword", "text/plain"})
    public Response getFile() {
        File file = new File(FILE_PATH);
        return Response.ok(file).header("Content-Disposition", "attachment; filename=\"file_from_server.log\"").build();
    }
}
