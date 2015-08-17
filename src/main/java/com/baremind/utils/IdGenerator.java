package com.baremind.utils;

import java.util.Date;

/**
 * Created by fixopen on 17/8/15.
 */
public class IdGenerator {
    private static short instanceId = 0;
    private static int previousTimestamp = 0;
    private static short serialNo = 0;
    public static long getNewId() {
        long result = (long)instanceId << 48;
        Date now = new Date();
        int timestamp = (int)now.getTime();
        if (timestamp == previousTimestamp) {
            serialNo++;
        } else {
            previousTimestamp = timestamp;
            serialNo = 0;
        }
        result |= timestamp << 16;
        result |= serialNo;
        return result;
    }
}
