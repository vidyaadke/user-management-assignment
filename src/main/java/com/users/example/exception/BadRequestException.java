package com.users.example.exception;

import com.users.example.gen.model.ErrorResponse;

public class BadRequestException extends RuntimeException {

    private ErrorResponse errorResponse;

    public BadRequestException(ErrorResponse errorResponse) {
        super();
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}