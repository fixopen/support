package com.baremind;

import com.baremind.data.Account;
import com.baremind.data.Organization;
import com.baremind.utils.IdGenerator;
import com.baremind.utils.JPAEntry;

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
            result = Response.status(404).build();
            Organization organization = JPAEntry.getObject(Organization.class, "id", id);
            if (organization != null) {
                result = Response.ok(organization).build();
            }
        }
        return result;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUser(@CookieParam("sessionId") String sessionId, Organization organization) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            organization.setId(IdGenerator.getNewId());
            EntityManager em = JPAEntry.getEntityManager();
            em.getTransaction().begin();
            em.persist(organization);
            Account account = new Account();
            account.setId(IdGenerator.getNewId());
            account.setSubjectId(organization.getId());
            account.setSubjectType("Organization");
            account.setActive(0);
            account.setType(0);
            account.setLoginName(organization.getName());
            account.setPassword(organization.getName());
            em.persist(account);
            em.getTransaction().commit();
            result = Response.ok(account).build();
        }
        return result;
    }
}
