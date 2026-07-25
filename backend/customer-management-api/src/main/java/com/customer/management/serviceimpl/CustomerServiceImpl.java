package com.customer.management.serviceimpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.customer.management.entity.Customer;
import com.customer.management.model.CustomerInputRequest;
import com.customer.management.model.CustomerResponse;
import com.customer.management.repository.CustomerRepository;
import com.customer.management.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        // Constructor implementation
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse createCustomer(CustomerInputRequest customerInfo) {
        // Implementation for creating a customer
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .firstName(customerInfo.getFirstName())
                .lastName(customerInfo.getLastName())
                .dateOfBirth(customerInfo.getDateOfBirth())
                .createdAt(LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.UTC)))
                .build());
        
        return CustomerResponse.builder()
                .id(savedCustomer.getId())
                .firstName(savedCustomer.getFirstName())
                .lastName(savedCustomer.getLastName())
                .dateOfBirth(savedCustomer.getDateOfBirth().toString())
                .build();
    }

    @Override
    public List<CustomerResponse> getAllCustomers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        
        List<Customer> customers = customerRepository.findAll(pageable).getContent();
        
        return customers.stream().map(customer -> CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dateOfBirth(customer.getDateOfBirth().toString())
                .build()).toList();
    }

}
