package com.baremind;

import com.baremind.algorithm.Securities;
import com.baremind.data.Resource;
import com.baremind.data.UploadLog;
import com.baremind.data.UploadMeta;
import com.baremind.utils.Hex;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("resources")
public class Resources {
    private static final String PERSISTENCE_UNIT_NAME = "sd";
    private static EntityManagerFactory factory;

    @POST
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

        @Override
        public void run() {
            //thread:

            // set state to processing
            uploadLog.setState(1);
            em.persist(uploadLog);
            // uncompress zip
            // get _meta.json
            // parse it
            Gson json = new Gson();
            Reader reader = new InputStreamReader(new FileInputStream(file));
            UploadMeta meta = json.fromJson(reader, UploadMeta.class);
            // insert to resource-table
            // move cover & content file to spec folder
            // calc content file digest
            InputStream inputStream = new FileInputStream(mediaFile);
            String d = Hex.bytesToHex(Securities.digestor.digest(inputStream));
            resource.digest = d;
            // crypto content file -- options
            // set state to processed
            uploadLog.setState(2);
            em.persist(uploadLog);

            // copyright insert
            // right transfer insert
            // resource transfer insert
        }

        UploadLog uploadLog;
        EntityManager em;
    }

    @POST
    @FormParam("uploadedFile")
    @Consumes({"application/octet-stream", "application/zip", "application/x-compressed"}) //MediaType.APPLICATION_OCTET_STREAM_TYPE
    public Response postZip(@Context HttpServletRequest request) {
        Response result = null;

        //{"metaInfo":{"message":"upload success","code":0},"data":"15"}

        //FileItemFactory factory = new DiskFileItemFactory();
        //ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            int length = request.getContentLength();
            byte[] buffer = new byte[length];
            ServletInputStream servletInputStream = request.getInputStream();
            servletInputStream.read(buffer);

            File zipFile = File.createTempFile("prefix", ".zip");
            FileOutputStream w = new FileOutputStream(zipFile);
            w.write(buffer);
            w.close();

            //insert record to table[id, time, uploader_id, file_path, status: 0(received) 1(processing) 2(processed)]
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            EntityManager em = factory.createEntityManager();
            UploadLog uploadLog = new UploadLog();
            //uploadLog
            //...
            em.persist(uploadLog);

            new UploadPostProcessor(uploadLog, em).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GET
    @Path("{stateId}")
    public Response queryState(@PathParam("stateId") Long id) {
        Response result = null;
        //{"metaInfo":{"message":"鏌ヨ鎴愬姛","code":0},"data":9}
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
    @Produces({{"application/pdf", "application/msword", "text/plain"})
    public Response getFile() {
        File file = new File(FILE_PATH);
        return Response.ok(file).header("Content-Disposition", "attachment; filename=\"file_from_server.log\"").build();
    }
}
