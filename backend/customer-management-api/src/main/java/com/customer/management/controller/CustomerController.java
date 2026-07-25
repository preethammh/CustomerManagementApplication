package com.customer.management.controller;

import org.springframework.web.bind.annotation.RestController;

import com.customer.management.model.CustomerInputRequest;
import com.customer.management.model.CustomerResponse;
import com.customer.management.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/customer-management")
public class CustomerController {

  private final CustomerService customerService;
  
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping("/customer")
  @Operation(
        summary = "Creates a new customer profile in the system.", 
        description = "Creates a new customer profile with the provided information."
    )
  @ApiResponse(responseCode = "200", description = "Successfully created the customer")
  @ApiResponse(responseCode = "400", description = "Invalid customer data provided")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerInputRequest customerInfo) {
      CustomerResponse response = customerService.createCustomer(customerInfo);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
  
  @GetMapping("/customers")
  @Operation(
        summary = "Retrieves a list of all customer profiles.", 
        description = "Fetches all customer profiles from the system."
    )
  @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of customers")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  public ResponseEntity<List<CustomerResponse>> getAllCustomers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "firstName") String sortBy) {
      // Implementation for retrieving all customers
      List<CustomerResponse> customers = customerService.getAllCustomers(page, size, sortBy);
      return ResponseEntity.ok(customers);
  }
  
}
