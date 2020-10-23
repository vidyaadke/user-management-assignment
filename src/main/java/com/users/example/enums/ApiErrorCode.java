package com.users.example.enums;

public enum ApiErrorCode implements HasCode {

    INTERNAL_SERVER_ERROR {
        @Override public int getStatusCode() {
            return HttpStatusCodes.InternalServerError.getStatusCode();
        }
    }, PARAMETER_INVALID_EMPTY {
        @Override public int getStatusCode() {
            return HttpStatusCodes.BadRequest.getStatusCode();
        }
    }, PARAMETER_INVALID_VALUE {
        @Override public int getStatusCode() {
            return HttpStatusCodes.BadRequest.getStatusCode();
        }
    }, PARAMETER_NULL {
        @Override public int getStatusCode() {
            return HttpStatusCodes.BadRequest.getStatusCode();
        }
    }

}
