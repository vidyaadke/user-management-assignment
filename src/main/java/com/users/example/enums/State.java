package com.users.example.enums;

public enum State implements HasDescription {

    ACT {
        @Override public String getDescription() {
            return "ACT";
        }
    }, NSW {
        @Override public String getDescription() {
            return "NSW";
        }
    }, NT {
        @Override public String getDescription() {
            return "NT";
        }
    }, QLD {
        @Override public String getDescription() {
            return "QLD";
        }
    }, SA {
        @Override public String getDescription() {
            return "SA";
        }
    }, TAS {
        @Override public String getDescription() {
            return "TAS";
        }
    }, VIC {
        @Override public String getDescription() {
            return "VIC";
        }
    }, WA {
        @Override public String getDescription() {
            return "WA";
        }
    },
    ;

}