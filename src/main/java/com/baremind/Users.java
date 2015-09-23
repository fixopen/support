package com.baremind;

import com.baremind.data.Account;
import com.baremind.data.User;
import com.baremind.utils.IdGenerator;
import com.baremind.utils.JPAEntry;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/20 0020.
 */
@Path("user")
public class Users {
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


    @GET
    @Path("session")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrBySessionId(@CookieParam("sessionId") String sessionId) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();
            Account account= JPAEntry.getAccount(sessionId);
            if(account != null){
                User user = JPAEntry.getObject(User.class,"id", account.getSubjectId());
                if (user != null) {
                    Map map = new HashMap<>();
                    map.put("user",user);
                    map.put("account",account);
                    result = Response.ok(new Gson().toJson(map), MediaType.APPLICATION_JSON).build();
                }
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
