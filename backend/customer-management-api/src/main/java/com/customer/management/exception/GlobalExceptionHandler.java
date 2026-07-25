package com.customer.management.exception;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.customer.management.model.CustomerCreationErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomerCreationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
      CustomerCreationErrorResponse errorResponse = CustomerCreationErrorResponse.builder()
              .message("Validation failed for the request.")
              .errors(ex.getBindingResult().getFieldErrors().stream()
                      .map(error -> error.getField() + ": " + error.getDefaultMessage())
                      .collect(Collectors.toList()))
              .build();
      return ResponseEntity.badRequest().body(errorResponse);
  }
}
