package com.customer.management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String dateOfBirth;
}
