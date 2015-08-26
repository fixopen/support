package com.baremind;

import com.baremind.data.Copyright;
import com.baremind.data.ResourceTransfer;
import com.baremind.utils.JPAEntry;

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
    public Response post(@CookieParam("sessionId") String sessionId,@PathParam("no") String no) {
        Response result = Response.status(401).build();
        EntityManager em = JPAEntry.getEntityManager();
        if (JPAEntry.isLogining(sessionId)) {
            Copyright copyright = JPAEntry.getObject(Copyright.class,"Copyright","no",no);
            if(copyright != null ){
                List<ResourceTransfer> list = JPAEntry.getList(ResourceTransfer.class,"ResourceTransfer","copyrightId",copyright.getId());
                result = Response.ok(list).build();
            }
        }
        return result;
    }
}
