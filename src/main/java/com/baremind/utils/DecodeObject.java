package com.baremind.utils;

import com.baremind.data.Resource;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by gaolianli on 2015/9/25.
 */
public class DecodeObject {
    public static <T> T decodeUTF8(T t, Class<T> c) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(t);
        try {
            jsonStr = URLDecoder.decode(jsonStr, "UTF-8");
            t = gson.fromJson(jsonStr, c);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return t;
    }
}
