package com.users.example.exception;

import com.users.example.gen.model.ErrorResponse;

public class ResourceNotFoundException extends RuntimeException {

    private ErrorResponse errorResponse;

    public ResourceNotFoundException(ErrorResponse errorResponse) {
        super();
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}