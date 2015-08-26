package com.baremind;

import com.baremind.data.Account;
import com.baremind.data.Organization;
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
@Path("organizations")
public class OrganizationAct {
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@CookieParam("sessionId") String sessionId, @PathParam("id") Long id) {
        Response result = Response.status(401).build();
            if (JPAEntry.isLogining(sessionId)) {
            String sql = "SELECT o FROM Organization o WHERE o.id=:id";
            EntityManager em = JPAEntry.getEntityManager();
            Organization org = em.createQuery(sql ,Organization.class).setParameter("id",id).getSingleResult();
            if(org!= null){
                result = Response.ok(new Gson().toJson(org),MediaType.APPLICATION_JSON).build();
            }else{
                result = Response.status(404).build();
            }
        }
        return result;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUser(@CookieParam("sessionId") String sessionId, Organization org) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            org.setId(IdGenerator.getNewId());
//            {"no":"sf","name":"sfsf",}
            EntityManager em = JPAEntry.getEntityManager();
            em.getTransaction().begin();
            em.persist(org);
//            em.getTransaction().commit();
            Account account = new Account();
            account.setId(IdGenerator.getNewId());
            account.setSubjectId(10000l);
            account.setSubjectType("Organization");
            account.setActive(0);
            account.setType(0);
            account.setLoginName(org.getName());
            account.setPassword(org.getName());
//            em.getTransaction().begin();
            em.persist(account);
            em.getTransaction().commit();

            result = Response.ok(account).build();
        }
        return result;
    }

}
