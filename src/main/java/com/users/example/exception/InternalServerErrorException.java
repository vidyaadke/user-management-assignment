package com.users.example.exception;

import com.users.example.gen.model.ErrorResponse;

public class InternalServerErrorException extends RuntimeException {

    private ErrorResponse errorResponse;

    public InternalServerErrorException(ErrorResponse errorResponse) {
        super();
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

}