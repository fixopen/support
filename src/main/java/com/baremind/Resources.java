package com.baremind;

import com.baremind.data.Resource;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("resources")
public class Resources {
    @POST
    public Resource post(@CookieParam("sessionId") String sessionId, Resource resource) {
        //find account from accounts-table by sessionId
        //and last operation time > now - 30min
        //update last operation time
        //store resource to resources-table
        return resource;
    }

    @GET
    public List<Resource> get() {
        return new ArrayList<Resource>();
    }

    @GET
    @Path("{id}")
    public Resource getById(@CookieParam("sessionId") String sessionId, @PathParam("id") Long id) {
        return new Resource();
    }

    public Resource postContent() {
        return new Resource();
    }

    public Resource getContent() {
        return new Resource();
    }

    public Resource postZip() {
        return new Resource();
    }
}
