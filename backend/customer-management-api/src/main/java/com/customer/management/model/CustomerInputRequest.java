package com.customer.management.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInputRequest {

   @NotNull(message = "First name is required")
   private String firstName;
  
   private String lastName;

   @NotNull(message = "Date of birth is required")
   @Past(message = "Date of birth must be in the past")
   private LocalDate dateOfBirth;

}
