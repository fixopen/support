package com.baremind;

import com.baremind.data.Resource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
//import java.net.URI;
//import java.nio.file.*;
//import java.util.Iterator;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("my-resource")
public class MyResource {
    private static final String PERSISTENCE_UNIT_NAME = "sd";
    private static EntityManagerFactory factory;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Resource getById(@PathParam("id") Long id) {
        Resource result = new Resource();
        return result;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Resource insertResource(Resource post) {

        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        NewCookie cookie = new NewCookie("sessionId", "xyazdfas", "/api/", null, null, -1, false);
        Response response = Response.ok().header("Set-Cookie", cookie.toString()+ ";HttpOnly").entity("body").build();

//        // read the existing entries and write to console
//        Query q = em.createQuery("select t from Todo t");
//        List<Todo> todoList = q.getResultList();
//        for (Todo todo : todoList) {
//            System.out.println(todo);
//        }
//        System.out.println("Size: " + todoList.size());

        // create new todo
        em.getTransaction().begin();
        Resource todo = new Resource();
        todo.setId(post.getId());
        todo.setName(post.getName());
        em.persist(todo);
        em.getTransaction().commit();

        em.close();
        //insert to database

        //java.nio.file.Files.copy(Path source, Path target, CopyOption... options)；

        try {
            CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.move(Paths.get("source"), Paths.get("destination"), options);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File source = new File("D:\\polo\\");
        File desc = new File("E:\\polo2\\");
        try {
            FileUtils.copyDirectory(source, desc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{

            File source = new File("D:\\polo\\");
            File desc = new File("E:\\polo2\\");

            if(source .renameTo(new File("E:\\polo2\\" + afile.getName()))){
                System.out.println("File is moved successful!");
            }else{
                System.out.println("File is failed to move!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        File source = new File("D:\\polo\\fileold");
        File desc = new File("E:\\polo2\\filenew");
        IOUtils.copy(source, desc);

        File[] reviews = null;
        for(File sortedRevDir : sortedRevDirs) {
            reviews = sortedRevDir.listFiles();
            int numFiles = 90;
            int numTwoThirds = 60;
            int numOneThirds = numFiles - numTwoThirds;

            String trainingDir = sortedRevDir.getAbsolutePath() + "/trainingData";
            File trDir = new File(trainingDir);
            trDir.mkdir();
            String testDir = sortedRevDir.getAbsolutePath() + "/testData";
            File tsDir = new File(testDir);
            tsDir.mkdir();

            for(int i = 0; i < numTwoThirds; i++) {
                File review = reviews[i];
                if(!review.isDirectory()) {
                    File reviewCopied = new File(trDir + "/" + review.getName());
                    review.renameTo(reviewCopied);
                }
            }
            for(int j = 0; j < numOneThird; j++) {
                File review = reviews[j];
                if(!review.isDirectory()) {
                    File reviewCopied = new File(tsDir + "/" + review.getName());
                    review.renameTo(reviewCopied);
                }
            }
        }

        return post;
    }
}
