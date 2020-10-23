package com.users.example.enums;

public enum Title implements HasDescription {
    Mr {
        @Override public String getDescription() {
            return "Mr";
        }
    }, Mrs {
        @Override public String getDescription() {
            return "Mrs";
        }
    },
}
