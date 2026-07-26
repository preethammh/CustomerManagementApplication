package com.customer.management.exception;

import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.customer.management.model.CustomerCreationErrorResponse;

@RestControllerAdvice
@Log4j2
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

  @ExceptionHandler(CustomerManagementException.class)
  public  ResponseEntity<CustomerCreationErrorResponse> handleCustomerManagementException(CustomerManagementException ex) {
      log.error("Error occurred while processing customer management request: {}", ex.getMessage(), ex);
      CustomerCreationErrorResponse errorResponse = CustomerCreationErrorResponse.builder()
                .message(ex.getMessage())
                .build();
      return ResponseEntity.status(500).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomerCreationErrorResponse> handleException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        CustomerCreationErrorResponse errorResponse = CustomerCreationErrorResponse.builder()
                .message("An unexpected error occurred. Please try again later.")
                .build();
        return ResponseEntity.status(500).body(errorResponse);
  }
}
