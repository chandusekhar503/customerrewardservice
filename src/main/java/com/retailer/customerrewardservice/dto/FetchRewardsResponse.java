package com.retailer.customerrewardservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FetchRewardsResponse {

    private String customerId;
    private String customerName;
    private List<Rewards> rewards;

}
