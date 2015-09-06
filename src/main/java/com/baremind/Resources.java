package com.baremind;

import com.baremind.algorithm.Securities;
import com.baremind.data.*;
import com.baremind.utils.Hex;
import com.baremind.utils.IdGenerator;
import com.baremind.utils.JPAEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.eclipse.persistence.exceptions.DatabaseException;

import javax.persistence.EntityManager;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("resources")
public class Resources {
//    private static final String FILE_PATH = "c:\\file.log";
//    private static final String BOOKS = "D:\\var\\file\\books\\";
//    private static final String COVERS = "D:\\var\\file\\covers\\";
//    private static final String ZIP_FILES = "D:\\var\\files\\";
//    private static final String ZIP_TEMPORARY = "D:\\var\\zipFiles\\";

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
    public Response get(@CookieParam("sessionId") String sessionId, @QueryParam("filter") @DefaultValue("") String filter) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            Map<String, Object> filterObject = null;
            if (filter != "") {
                String rawFilter = URLDecoder.decode(filter);
                filterObject = new Gson().fromJson(rawFilter, new TypeToken<Map<String, Object>>() {
                }.getType());
            }
            List<Resource> resources = JPAEntry.getList(Resource.class, filterObject);
            result = Response.ok(new Gson().toJson(resources)).build();
        }
        return result;
    }

    @GET
    @Path("{no}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@CookieParam("sessionId") String sessionId, @PathParam("no") String no) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();
            Resource resource = JPAEntry.getObject(Resource.class, "no", no);
            if (resource != null) {
                result = Response.ok(resource).build();
            }
        }
        return result;
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
            File fileZip = new File(Securities.config.ZIP_TEMPORARY);
            Securities.zip.uncompress(file, fileZip);
            try {
                Reader reader = new InputStreamReader(new FileInputStream(Securities.config.ZIP_TEMPORARY + "__meta.json"));
                Gson json = new Gson();
                UploadMeta meta = json.fromJson(reader, UploadMeta.class);
                Resource resource = new Resource(meta);
//                Resource r = JPAEntry.getObject(Resource.class,"no",resource.getNo());
                resource.setId(IdGenerator.getNewId());
                File metaFile = new File(Securities.config.ZIP_TEMPORARY + "__meta.json");
                metaFile.deleteOnExit();

//                CopyOption[] options = new CopyOption[]{
//                        StandardCopyOption.REPLACE_EXISTING,
//                        StandardCopyOption.COPY_ATTRIBUTES
//                };
//                Files.move(Paths.get("D:\\var\\zipFiles\\"+resource.getFilePath()), Paths.get("D:\\var\\zipFilesSave\\"+resource.getFilePath()),options);
//                Files.move(Paths.get("D:\\var\\zipFiles\\"+resource.getCover()), Paths.get("D:\\var\\zipFilesSave\\"+resource.getCover()),options);

                File source = new File(Securities.config.ZIP_TEMPORARY + resource.getFilePath());
                File desc = new File(Securities.config.ZIP_TEMPORARY + resource.getCover());

                if (source.renameTo(new File(Securities.config.BOOKS + resource.getFilePath()))) {
                    if (desc.renameTo(new File(Securities.config.COVERS + resource.getCover()))) {
                        System.out.println("File is moved successful!");
                    } else {
                        System.out.println("File is failed to move!");
                    }
                } else {
                    System.out.println("File is failed to move!");
                }

                InputStream inputStream = new FileInputStream(Securities.config.BOOKS + resource.getFilePath());
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
//                if( JPAEntry.getList(Resource.class,"no",resource.getNo()).size() == 0) {
//                }
                uploadLog.setResourceNo(resource.getNo());
                uploadLog.setState(9);
                em.persist(uploadLog);
                em.getTransaction().commit();

                em.getTransaction().begin();
                em.persist(resource);
                em.persist(copyright);
                em.getTransaction().commit();

            }catch (IOException | DatabaseException e) {
                e.printStackTrace();
            }
        }
    }

    @POST
    @Consumes({"application/octet-stream", "application/zip", "application/x-compressed"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response postZip(@Context HttpServletRequest request, @CookieParam("sessionId") String sessionId) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            try {
                byte[] buffer = new byte[4 * 1024];

                File zipFile = new File(Securities.config.ZIP_FILES + IdGenerator.getNewId() + ".zip");
//                zipFile.setWritable(true, false);
                //szipFile.createNewFile();
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
        }
        return result;
    }

    @GET
    @Path("uploadState/{id}")
    public Response queryState(@CookieParam("sessionId") String sessionId, @PathParam("id") Long id) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            UploadLog uploadLog = JPAEntry.getObject(UploadLog.class, "id", id);
            result = Response.ok("{\"metaInfo\":{\"message\":\"\",\"code\":0},\"data\":" + uploadLog.getState() + "}").build();
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
    @Produces({"application/pdf", "application/zip", "application/msword", "text/plain"})
    public Response getFile(@CookieParam("sessionId") String sessionId, @PathParam("no") String no) throws FileNotFoundException {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();
            List<Resource> resource = JPAEntry.getList(Resource.class, "no",no);
            if (resource != null) {
                result = Response.status(404).build();
                Copyright copyright = JPAEntry.getObject(Copyright.class, "resourceId", resource.get(0).getId());
                if (copyright != null) {
                    File file = new File(Securities.config.BOOKS + resource.get(0).getFilePath());
                    if (file.exists()) {
//                        FileInputStream in = null;
//                        try {
//                           in = new FileInputStream(file);
//                            byte[] data = new byte[(int) file.length()];
//                            in.read(data);
//                            in.close();
//                        } catch (IOException e) {
//                            result = Response.status(500).build();
//                            e.printStackTrace();
//                        }

                        //save resource transfer
                        //资源摘要  --》 交给我个签名  --》然后下载
//                        ResourceTransfer resourceTransfer = new ResourceTransfer();
//                        resourceTransfer.setId(IdGenerator.getNewId());
//                        resourceTransfer.setResourceId(resource.get(0).getId());
//                        resourceTransfer.setRightTransferId();

                        result = Response.ok(file, "application/octet-stream").header("Content-Disposition", "attachment; filename=\"" + resource.get(0).getFilePath() + "\"").build();
                    } else {
                        result = Response.status(404).build();
                    }/* catch (IOException e) {
                        result = Response.status(500).build();
                        e.printStackTrace();
                    }*/
                }
            }
        }
        return result;
    }

    @GET
    @Path("{no}/cover")
    @Produces({"image/jpeg"})
    public Response getImage(@CookieParam("sessionId") String sessionId, @PathParam("no") String no) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();
            List<Resource> resource = JPAEntry.getList(Resource.class, "no", no);
            if (resource != null) {
                File file = new File(Securities.config.COVERS + resource.get(0).getCover());
                if (file.exists()) {
//                    file.createNewFile();
//                    FileInputStream in = null;
//                    try {
//                        in = new FileInputStream(file);
//                        byte[] data = new byte[(int) file.length()];
//                        in.read(data);
//                        in.close();
//                    }catch (IOException e) {
//                        result = Response.status(500).build();
//                        e.printStackTrace();
//                    }

                    result = Response.ok(file).header("Content-Disposition", "attachment; filename=\"" + resource.get(0).getCover() + "\"").build();
                } else {
                    result = Response.status(404).build();
                }
            }
        }
        return result;
    }
}
