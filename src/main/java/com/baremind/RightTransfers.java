package com.baremind;

import com.baremind.data.Copyright;
import com.baremind.data.Resource;
import com.baremind.data.RightTransfer;
import com.baremind.utils.JPAEntry;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Administrator on 2015/8/25 0025.
 */
@Path("rightTransfers")
public class RightTransfers {
    @GET
    @Path("{no}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(@CookieParam("sessionId") String sessionId, @PathParam("no") String no) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();
            List<Resource> resource = JPAEntry.getList(Resource.class, "no", no);
            if (resource != null) {
                Copyright copyright = JPAEntry.getObject(Copyright.class, "resourceId", resource.get(0).getId());
                if (copyright != null) {
                    List<RightTransfer> list = JPAEntry.getList(RightTransfer.class, "copyrightId", copyright.getId());
                    result = Response.ok(new Gson().toJson(list)).build();
                }
            }
        }
        return result;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@CookieParam("sessionId") String sessionId) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();
            String sql = "SELECT r FROM RightTransfer r ";
            EntityManager em = JPAEntry.getEntityManager();
            List<RightTransfer> list = em.createQuery(sql,RightTransfer.class).getResultList();
            result = Response.ok(new Gson().toJson(list)).build();
        }
        return result;
    }
}
