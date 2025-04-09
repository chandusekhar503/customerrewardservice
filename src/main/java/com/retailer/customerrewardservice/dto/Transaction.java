package com.retailer.customerrewardservice.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Transaction {

    private String transactionId;
    private String customerId;
    private double transactionAmount;
    private LocalDateTime transactionDateTime;
}
