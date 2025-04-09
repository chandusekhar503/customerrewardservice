package com.retailer.customerrewardservice.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private String id;
    private String name;
}
