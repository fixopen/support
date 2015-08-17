package com.baremind;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("rights")
public class Rights {
    public Right transfer() {
        return new Right();
    }

    public List<Right> query() {
        return new ArrayList<Right>();
    }
}
