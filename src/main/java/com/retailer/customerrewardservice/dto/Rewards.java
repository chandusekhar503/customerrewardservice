package com.retailer.customerrewardservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Rewards {
    private int month;
    private String monthName;
    private int rewardPoints;

}
