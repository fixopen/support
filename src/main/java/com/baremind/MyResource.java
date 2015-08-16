package com.baremind;

import com.baremind.data.Resource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
        return post;
    }
}
