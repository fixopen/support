package com.baremind.algorithm;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fixopen on 3/6/15.
 */
public class Digestor {
    byte[] digest(String data) {
        byte[] result = null;
        try {
            MessageDigest d = MessageDigest.getInstance("SHA-256");
            d.update(data.getBytes());
            result = d.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    byte[] digest(InputStream inputStream) {
        byte[] result = null;
        try {
            MessageDigest d = MessageDigest.getInstance("SHA-256");
            for (; ; ) {
                byte[] buffer = new byte[4096];
                int length = inputStream.read(buffer);
                if (length == 0) {
                    break;
                }
                d.update(buffer, 0, length);
                if (length < 4096) {
                    break;
                }
            }
            result = d.digest();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
