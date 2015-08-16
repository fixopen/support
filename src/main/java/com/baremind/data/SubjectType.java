package com.baremind.data;

/**
 * Created by fixopen on 16/8/15.
 */
public enum SubjectType {
    Organization {
        @Override
        public String toString() {
            return "Organization";
        }
    },
    Personal {
        @Override
        public String toString() {
            return "Personal";
        }
    };
}
