package com.baremind.data;

/**
 * Created by fixopen on 16/8/15.
 */
public enum SubjectType {
    Organization {
        @Override
        public String toString() {
            return "Organizations";
        }
    },
    Personal {
        @Override
        public String toString() {
            return "Personal";
        }
    };

//    public static SubjectType valueOf(String s) {
//        SubjectType result = null;
//        switch (s) {
//            case "Organizations":
//                result = Organizations;
//                break;
//            case "Personal":
//                result = Personal;
//                break;
//        }
//        return result;
//    }
}
