package com.customer.management.serviceimpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import com.customer.management.exception.CustomerManagementException;
import com.customer.management.model.CustomerPaginatedResponse;
import com.customer.management.model.Metadata;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
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
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        // Constructor implementation
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse createCustomer(CustomerInputRequest customerInfo) {
        log.debug("Creating customer with first name: {} and last name: {}", customerInfo.getFirstName(), customerInfo.getLastName());
        // Implementation for creating a customer
        try{
            Customer savedCustomer = customerRepository.save(Customer.builder()
                    .firstName(customerInfo.getFirstName())
                    .lastName(customerInfo.getLastName())
                    .dateOfBirth(customerInfo.getDateOfBirth())
                    .createdAt(LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.UTC)))
                    .build());

            log.debug("Customer with id: {} has been created", savedCustomer.getId());
            return CustomerResponse.builder()
                    .id(savedCustomer.getId())
                    .firstName(savedCustomer.getFirstName())
                    .lastName(savedCustomer.getLastName())
                    .dateOfBirth(savedCustomer.getDateOfBirth().toString())
                    .build();
        }catch (Exception ex){
            throw new CustomerManagementException("Error while saving customer " + ex.getMessage(), ex);
        }
    }

    @Override
    public CustomerPaginatedResponse getAllCustomers(int page, int size, String sortBy) {
        log.debug("Getting all customers with page {} and size {}", page, size);

        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

            Page<Customer> customerPage = customerRepository.findAll(pageable);

            log.debug("Found {} customers", customerPage.getTotalElements());
            List<CustomerResponse> customerResponses = customerPage.getContent().stream().map(customer -> CustomerResponse.builder()
                    .id(customer.getId())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .dateOfBirth(customer.getDateOfBirth().toString())
                    .build()).toList();

            return CustomerPaginatedResponse.builder()
                    .customers(customerResponses)
                    .metadata(Metadata.builder()
                            .currentPage(pageable.getPageNumber())
                            .perPage(pageable.getPageSize())
                            .totalItems(customerPage.getTotalElements())
                            .totalPages(customerPage.getTotalPages())
                            .build())
                    .build();
        }catch (Exception ex){
            throw new CustomerManagementException("Error while retrieving customers " + ex.getMessage(), ex);
        }
    }

}
