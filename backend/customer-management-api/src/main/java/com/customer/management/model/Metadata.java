package com.customer.management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Metadata {
    private int currentPage;
    private int perPage;
    private long totalPages;
    private long totalItems;
}
