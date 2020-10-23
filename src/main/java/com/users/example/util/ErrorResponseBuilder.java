package com.users.example.util;

import com.users.example.gen.model.ErrorDetail;
import com.users.example.gen.model.ErrorResponse;

import java.util.List;

public class ErrorResponseBuilder {

    /*
     * Function to build error response
     */
    public ErrorResponse buildErrorResponse(List<ErrorDetail> errorDetails) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errorDetails);
        return errorResponse;
    }

    /*
     * Function to build error response
     */
    public ErrorDetail buildErrorDetail(String errorCode, String message, String parameter) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setErrorCode(errorCode);
        errorDetail.setMessage(message);
        errorDetail.setParameter(parameter);
        return errorDetail;
    }
}
