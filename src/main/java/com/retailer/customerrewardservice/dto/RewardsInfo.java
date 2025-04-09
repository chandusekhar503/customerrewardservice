package com.retailer.customerrewardservice.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RewardsInfo {
    private List<Rewards> rewards;
}
