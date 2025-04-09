package com.retailer.customerrewardservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerRewardException.class)
    public ResponseEntity<Map<String, String>> handleCustomerRewardException(CustomerRewardException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("errorCode", ex.getErrorCode());
        error.put("errorMessage", ex.getErrorDescription());
        return new ResponseEntity<>(error,ex.getHttpStatusCode());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("errorCode","4001");
        errors.put("errorMessage","Validation error");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}