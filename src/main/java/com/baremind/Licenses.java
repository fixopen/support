package com.baremind;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("licenses")
public class Licenses {
    @GET
    public String get() {
        return "";
    }
}
