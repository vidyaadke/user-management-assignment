package com.users.example.exception;

import com.users.example.gen.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ResponseEntity<ErrorResponse> handleException(
        InternalServerErrorException exception, final HttpServletRequest request) {
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ResponseEntity<ErrorResponse> handleBadReuest(
        BadRequestException exception, final HttpServletRequest request) {
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseEntity<ErrorResponse> handleResourceNotFoundException(
        ResourceNotFoundException exception, final HttpServletRequest request) {
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.NOT_FOUND);
    }

}