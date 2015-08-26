package com.baremind;

import com.baremind.data.Copyright;
import com.baremind.data.Resource;
import com.baremind.data.ResourceTransfer;
import com.baremind.utils.JPAEntry;

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
            result = Response.status(401).build();
            Resource resource = JPAEntry.getObject(Resource.class, "no", no);
            if (resource != null) {
                Copyright copyright = JPAEntry.getObject(Copyright.class, "resourceId", resource.getId());
                if (copyright != null) {
                    List<ResourceTransfer> list = JPAEntry.getList(ResourceTransfer.class, "copyrightId", copyright.getId());
                    result = Response.ok(list).build();
                }
            }
        }
        return result;
    }
}
