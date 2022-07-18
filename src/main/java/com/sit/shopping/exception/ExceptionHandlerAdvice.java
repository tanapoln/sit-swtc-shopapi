package com.sit.shopping.exception;

import com.sit.shopping.exception.EntityNotFoundException;
import com.sit.shopping.exception.ErrorResponse;
import com.sit.shopping.exception.InvalidCouponException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleNotFoundException(EntityNotFoundException e) {
        return handleException(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCouponException.class)
    public ResponseEntity handleInvalidCouponException(InvalidCouponException e) {
        return handleException(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> handleException(String message, HttpStatus status) {
        ErrorResponse response = new ErrorResponse(message, status.value());
        return ResponseEntity.status(status).body(response);
    }
}