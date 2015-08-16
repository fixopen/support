package com.baremind;

import com.baremind.data.Copyright;

import javax.ws.rs.Path;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("copyrights")
public class Copyrights {
    public Copyright post() {
        return new Copyright();
    }

    public void revoke() {
        //
    }
}
