package com.customer.management.service;

import java.util.List;

import com.customer.management.model.CustomerInputRequest;
import com.customer.management.model.CustomerPaginatedResponse;
import com.customer.management.model.CustomerResponse;

public interface CustomerService {

  CustomerResponse createCustomer(CustomerInputRequest customerInfo);

    CustomerPaginatedResponse getAllCustomers(int page, int size, String sortBy);
}
