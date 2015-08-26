package com.baremind;

import com.baremind.data.Account;
import com.baremind.data.User;
import com.baremind.utils.IdGenerator;
import com.baremind.utils.JPAEntry;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Administrator on 2015/8/20 0020.
 */
@Path("user")
public class UserAct {
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@CookieParam("sessionId") String sessionId, @PathParam("id") Long id) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();
            User user = JPAEntry.getObject(User.class, "id", id);
            if (user != null) {
               result = Response.ok(user, MediaType.APPLICATION_JSON).build();
            }
        }
        return result;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUser(@CookieParam("sessionId") String sessionId, User user) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            user.setId(IdGenerator.getNewId());
            EntityManager em = JPAEntry.getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            Account account = new Account();
            account.setId(IdGenerator.getNewId());
            account.setSubjectId(user.getId());
            account.setSubjectType("Personal");
            account.setActive(0);
            account.setType(0);
            account.setLoginName(user.getName());
            account.setPassword(user.getName());
            em.persist(account);
            em.getTransaction().commit();
            result = Response.ok(account).build();
        }
        return result;
    }
}
