package com.customer.management.service;

import com.customer.management.entity.Customer;
import com.customer.management.exception.CustomerManagementException;
import com.customer.management.model.CustomerInputRequest;
import com.customer.management.model.CustomerPaginatedResponse;
import com.customer.management.model.CustomerResponse;
import com.customer.management.repository.CustomerRepository;
import com.customer.management.serviceimpl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestCustomerManagementService {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private CustomerInputRequest inputRequest;
    private Customer customerEntity;

    @BeforeEach
    void setUp() {
        inputRequest = CustomerInputRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        customerEntity = Customer.builder()
                .id(101L)
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .createdAt(LocalDate.now().atStartOfDay())
                .build();
    }

    @Test
    @DisplayName("Should successfully create a customer and return response")
    void createCustomer_Success() {
        // Arrange
        when(customerRepository.save(any(Customer.class))).thenReturn(customerEntity);

        // Act
        CustomerResponse response = customerService.createCustomer(inputRequest);

        // Assert
        assertNotNull(response);
        assertEquals(101L, response.getId());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("1990-01-01", response.getDateOfBirth());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw CustomerManagementException when save fails")
    void createCustomer_ThrowsException_OnRepositoryFailure() {
        // Arrange
        when(customerRepository.save(any(Customer.class)))
                .thenThrow(new RuntimeException("Database connection timeout"));

        // Act & Assert
        CustomerManagementException exception = assertThrows(CustomerManagementException.class, () -> {
            customerService.createCustomer(inputRequest);
        });

        assertTrue(exception.getMessage().contains("Error while saving customer"));
        assertTrue(exception.getMessage().contains("Database connection timeout"));
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should return paginated customers response with correct metadata")
    void getAllCustomers_Success() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortBy = "lastName";
        Pageable expectedPageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        List<Customer> customerList = Collections.singletonList(customerEntity);
        Page<Customer> pageResult = new PageImpl<>(customerList, expectedPageable, 1);

        when(customerRepository.findAll(expectedPageable)).thenReturn(pageResult);

        // Act
        CustomerPaginatedResponse response = customerService.getAllCustomers(page, size, sortBy);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getCustomers().size());

        CustomerResponse customerRes = response.getCustomers().get(0);
        assertEquals(101L, customerRes.getId());
        assertEquals("John", customerRes.getFirstName());

        // Validate Meta Data
        assertNotNull(response.getMetadata());
        assertEquals(0, response.getMetadata().getCurrentPage());
        assertEquals(10, response.getMetadata().getPerPage());
        assertEquals(1, response.getMetadata().getTotalItems());
        assertEquals(1, response.getMetadata().getTotalPages());

        verify(customerRepository, times(1)).findAll(expectedPageable);
    }

    @Test
    @DisplayName("Should throw CustomerManagementException when pagination lookup fails")
    void getAllCustomers_ThrowsException_OnRepositoryFailure() {
        // Arrange
        int page = 0;
        int size = 5;
        String sortBy = "id";
        Pageable expectedPageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        when(customerRepository.findAll(expectedPageable))
                .thenThrow(new RuntimeException("Query failure"));

        // Act & Assert
        CustomerManagementException exception = assertThrows(CustomerManagementException.class, () -> {
            customerService.getAllCustomers(page, size, sortBy);
        });

        assertTrue(exception.getMessage().contains("Error while retrieving customers"));
        verify(customerRepository, times(1)).findAll(expectedPageable);
    }
}
