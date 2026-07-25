package com.customer.management.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CustomerCreationErrorResponse
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreationErrorResponse {

  private String message;
  private List<String> errors;
}
