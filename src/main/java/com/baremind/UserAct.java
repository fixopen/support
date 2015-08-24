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
            String sql = "SELECT u FROM User u WHERE u.id=:id";
            EntityManager em = JPAEntry.getEntityManager();
            User user = em.createQuery(sql ,User.class).setParameter("id",id).getSingleResult();
            if(user!= null){
               result = Response.ok(new Gson().toJson(user),MediaType.APPLICATION_JSON).build();
            }else{
               result = Response.status(404).build();
            }
        }
        return result;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUser(@CookieParam("sessionId") String sessionId, User user) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
//            {"id":123,"no":"sdf","name":"sdf","idType":123,"idNo":"adsf","organizationId":null,"companyName":null,"companyNo":null,"position":null,"adress":null}
            user.setId(IdGenerator.getNewId());
            EntityManager em = JPAEntry.getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
//            em.getTransaction().commit();
            //{"id":23,"subjectType":"ads","subjectId":12312,"active":0,"type":0,}
            Account account = new Account();
            account.setId(IdGenerator.getNewId());
            account.setSubjectId(10000l);
            account.setSubjectType("Personal");
            account.setActive(0);
            account.setType(0);
            account.setLoginName(user.getName());
            account.setPassword(user.getName());
//            em.getTransaction().begin();
            em.persist(account);
            em.getTransaction().commit();
            result = Response.ok(account).build();
        }
        return result;
    }

}
