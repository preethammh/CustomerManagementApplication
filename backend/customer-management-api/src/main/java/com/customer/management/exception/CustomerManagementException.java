package com.customer.management.exception;

public class CustomerManagementException extends RuntimeException {
  public CustomerManagementException(String message,Throwable cause) {
    super(message, cause);
  }
}
