package com.baremind;

import com.baremind.data.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fixopen on 16/8/15.
 */
public class Resources {
    public Resource post() {
        return new Resource();
    }

    public List<Resource> get() {
        return new ArrayList<Resource>();
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
