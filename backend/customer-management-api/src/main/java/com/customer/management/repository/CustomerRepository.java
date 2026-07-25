package com.customer.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.management.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
