package com.users.example.enums;

public enum HttpStatusCodes implements HasCode {
    OK() {
        @Override public int getStatusCode() {
            return 200;
        }
    }, BadRequest() {
        @Override public int getStatusCode() {
            return 400;
        }
    }, InternalServerError() {
        @Override public int getStatusCode() {
            return 500;
        }
    }, NotFound() {
        @Override public int getStatusCode() {
            return 404;
        }
    }
}
