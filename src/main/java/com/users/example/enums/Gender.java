package com.users.example.enums;

public enum Gender implements HasDescription {
    Male {
        @Override public String getDescription() {
            return "Male";
        }
    }, Female {
        @Override public String getDescription() {
            return "Female";
        }
    },
}
