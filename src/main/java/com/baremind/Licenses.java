package com.baremind;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("licenses")
public class Licenses {
    //请求资源   秘钥 resource_transfer --key
    @GET
    public String get() {
        return "";
    }
}
