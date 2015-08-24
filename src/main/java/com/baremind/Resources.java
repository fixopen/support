package com.baremind;

import com.baremind.algorithm.Securities;
import com.baremind.data.*;
import com.baremind.utils.Hex;
import com.baremind.utils.IdGenerator;
import com.baremind.utils.JPAEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("resources")
public class Resources {
    private static final String FILE_PATH = "c:\\file.log";
    private static final String BOOKS ="D:\\var\\file\\books\\";
    private static final String COVERS ="D:\\var\\file\\covers\\";
    private static final String ZIP_FILES ="D:\\var\\files\\";
    private static final String ZIP_TEMPORARY ="D:\\var\\zipFiles\\";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Resource post(@CookieParam("sessionId") String sessionId, Resource resource) {
        Account account = JPAEntry.getAccount(sessionId);
        if (account != null) {
            boolean isLogin = JPAEntry.isLogining(account);
            if (isLogin) {
                EntityManager em = JPAEntry.getEntityManager();
                em.getTransaction().begin();
                resource.setId(IdGenerator.getNewId());
                resource.setOwnerId(account.getId());
                em.persist(resource);
                em.getTransaction().commit();
            }
        }
        return resource;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Resource> get(@CookieParam("sessionId") String sessionId, @QueryParam("filter") @DefaultValue("") String filter) {
        List<Resource> result = new ArrayList<Resource>();
        if (JPAEntry.isLogining(sessionId)) {
            Map<String, Object> filterObject = null;
            if (filter != "") {
                String rawFilter = URLDecoder.decode(filter);
                filterObject = new Gson().fromJson(rawFilter, new TypeToken<Map<String, Object>>() {}.getType());
            }
            String sql = "SELECT r FROM Resource r WHERE 1 = 1";
            if (filterObject != null) {
                for (Map.Entry<String, Object> entry : filterObject.entrySet()) {
                    String key = entry.getKey();
                    sql += " AND r." + key + " = :" + key;
                }
            }
            EntityManager em = JPAEntry.getEntityManager();
            TypedQuery q = em.createQuery(sql, Resource.class);
            if (filterObject != null) {
                for (Map.Entry<String, Object> entry : filterObject.entrySet()) {
                    String key = entry.getKey();
                    q.setParameter(key, entry.getValue());
                }
            }
            result = q.getResultList();
        }
        return result;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
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
            em.getTransaction().begin();
            uploadLog.setState(3);
            em.persist(uploadLog);
            em.getTransaction().commit();

            File file = new File(uploadLog.getFilePath());
            File fileZip = new File(ZIP_TEMPORARY);
            Securities.zip.uncompress(file, fileZip);
            try {
                Reader reader = new InputStreamReader(new FileInputStream(ZIP_TEMPORARY + "__meta.json"));
                Gson json = new Gson();
                UploadMeta meta = json.fromJson(reader, UploadMeta.class);
                Resource resource = new Resource(meta);
                resource.setId(IdGenerator.getNewId());
//                CopyOption[] options = new CopyOption[]{
//                        StandardCopyOption.REPLACE_EXISTING,
//                        StandardCopyOption.COPY_ATTRIBUTES
//                };
//                Files.move(Paths.get("D:\\var\\zipFiles\\"+resource.getFilePath()), Paths.get("D:\\var\\zipFilesSave\\"+resource.getFilePath()),options);
//                Files.move(Paths.get("D:\\var\\zipFiles\\"+resource.getCover()), Paths.get("D:\\var\\zipFilesSave\\"+resource.getCover()),options);

                File source = new File(ZIP_TEMPORARY + resource.getFilePath());
                File desc = new File(ZIP_TEMPORARY + resource.getCover());

                if (source.renameTo(new File(BOOKS + resource.getFilePath()))) {
                    if (desc.renameTo(new File(COVERS + resource.getCover()))) {
                        System.out.println("File is moved successful!");
                    } else {
                        System.out.println("File is failed to move!");
                    }
                } else {
                    System.out.println("File is failed to move!");
                }

                InputStream inputStream = new FileInputStream(BOOKS + resource.getFilePath());
                String d = Hex.bytesToHex(Securities.digestor.digest(inputStream));
                resource.setDigest(d);

                Copyright copyright = new Copyright();
                copyright.setId(IdGenerator.getNewId());
                copyright.setNo(resource.getNo());
                copyright.setResourceId(resource.getId());
                copyright.setOwnerId(10000L);
                copyright.setAuthorId(10000L);
                copyright.setStatus(1);
                em.getTransaction().begin();
                em.persist(resource);
                em.persist(copyright);
                uploadLog.setState(9);
                em.persist(uploadLog);
                em.getTransaction().commit();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @POST
    @Consumes({"application/octet-stream", "application/zip", "application/x-compressed"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response postZip(@Context HttpServletRequest request, @CookieParam("sessionId") String sessionId) {
        Response result = null;
        if (JPAEntry.isLogining(sessionId)) {
            try {
                byte[] buffer = new byte[4 * 1024];

                File zipFile = new File(ZIP_FILES + IdGenerator.getNewId() + ".zip");
                FileOutputStream w = new FileOutputStream(zipFile);
                ServletInputStream servletInputStream = request.getInputStream();
                for (; ; ) {
                    int receiveLength = servletInputStream.read(buffer);
                    if (receiveLength == -1) {
                        break;
                    }
                    w.write(buffer, 0, receiveLength);
                }
                w.close();

                //insert record to table[id, time, uploader_id, file_path, status: 0(received) 1(processing) 2(processed)]
                EntityManager em = JPAEntry.getEntityManager();
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
        } else {
            result = Response.status(401).build();
        }
        return result;
    }

    @GET
    @Path("uploadState/{id}")
    public Response queryState(@CookieParam("sessionId") String sessionId, @PathParam("id") Long id) {
        Response result = null;
        if (JPAEntry.isLogining(sessionId)) {
            EntityManager em = JPAEntry.getEntityManager();
            String sql = "SELECT l FROM UploadLog l WHERE l.id = :id";
            UploadLog uploadLog = em.createQuery(sql, UploadLog.class).setParameter("id", id).getSingleResult();
            result = Response.ok("{\"metaInfo\":{\"message\":\"\",\"code\":0},\"data\":" + uploadLog.getState() + "}").build();
        } else {
            result = Response.status(401).build();
        }
        return result;
    }

//    @POST
//    @Consumes("multipart/form-data")
//    public Response postZipByFormData(MultipartFormDataInput input, MultivaluedMap<String, String> header, @MultipartForm FileUploadForm input) {
//        //
//    }


    @GET
    @Path("{no}")
    @Produces({"application/pdf", "application/msword", "text/plain"})
    public Response getFile(@CookieParam("sessionId") String sessionId, @PathParam("no") String no) {
        Response result = null;
//        if (JPAEntry.isLogining(sessionId, (Account a) -> {})) {
            String sql = "SELECT r FROM Resource r WHERE r.no = :no";
            EntityManager em = JPAEntry.getEntityManager();
            Resource resource = em.createQuery(sql, Resource.class).setParameter("no", no).getSingleResult();
            if (resource == null) {
                result = Response.status(404).build();
            } else {
                try {
                    //判断是否有copyright
//                    encrypt
                    //resource_transfer 资源流转记一下
                    File file = new File(BOOKS + resource.getFilePath());
                    file.length();
//                    File toFile = new File(resource.getFilePath());
                    if(file.exists()){
                        FileInputStream in = new FileInputStream(file);
                        byte[] data = new byte[4*1024];
                        in.read();
                        /*
                        * FileOutputStream fileOutput = new FileOutputStream(toFile);

                        byte[] data = new byte[4*1024];
                        int len;
                        while ((len = in.read(data)) != -1) {
                            fileOutput.write(data, 0, len);
                        }
                        in.close();
                        fileOutput.close();
                        * */

//                       result = Response.ok(fileOutput).header("Content-Disposition", "attachment; filename=\"file_from_server.log\"").build();
                        result = Response.ok(in).header("Content-Disposition", "attachment; filename=\"file_from_server.log\"").build();
                    }else {
                        result = Response.status(404).build();
                    }

                } catch (Exception e) {
                    result = Response.status(500).build();
                    e.printStackTrace();
                }
            }
//        } else {
//            result = Response.status(401).build();
//        }
        return result;
    }

    @GET
    @Path("{no}/cover")
    @Produces({"image/jpeg"})
    public Response getImage(@CookieParam("sessionId") String sessionId, @PathParam("no") String no) {
        Response result = null;
        if (JPAEntry.isLogining(sessionId, (Account a) -> {})) {
            String sql = "SELECT r FROM Resource r WHERE r.no = :no";
            EntityManager em = JPAEntry.getEntityManager();
            Resource resource = em.createQuery(sql, Resource.class).setParameter("no", no).getSingleResult();
            if (resource == null) {
                result = Response.status(404).build();
            } else {
                try {
                    File file = new File(COVERS + resource.getCover());
                    file.createNewFile();
                    if(file.exists()){
                        FileInputStream in = new FileInputStream(file);
                        FileOutputStream fileOutput = new FileOutputStream(file);

                        byte[] data = new byte[4*1024];
                        int len;
                        while ((len = in.read(data)) != -1) {
                            fileOutput.write(data, 0, len);
                        }
                        in.close();
                        fileOutput.close();

                        result = Response.ok(fileOutput).header("Content-Disposition", "attachment; filename=\"file_from_server.log\"").build();
                    }else {
                        result = Response.status(404).build();
                    }

                } catch (Exception e) {
                    result = Response.status(500).build();
                    e.printStackTrace();
                }
            }
        } else {
            result = Response.status(401).build();
        }
        return result;
    }
}
