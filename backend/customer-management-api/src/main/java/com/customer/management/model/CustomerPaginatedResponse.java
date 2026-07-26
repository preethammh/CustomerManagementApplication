package com.customer.management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerPaginatedResponse {
    private List<CustomerResponse> customers;
    private Metadata metadata;
}
