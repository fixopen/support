package com.baremind;

import javax.security.cert.Certificate;
import javax.ws.rs.Path;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("certificates")
public class Certificates {
    public Certificate grant() {
        return null;
    }

    public void revoke(Certificate certificate) {
        //
    }

    public Certificate query() {
        return null;
    }
}
