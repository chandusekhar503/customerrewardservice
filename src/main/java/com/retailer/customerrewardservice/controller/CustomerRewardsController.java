package com.retailer.customerrewardservice.controller;

import com.retailer.customerrewardservice.dto.FetchRewardsRequest;
import com.retailer.customerrewardservice.dto.FetchRewardsResponse;
import com.retailer.customerrewardservice.service.RewardsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rewards")
public class CustomerRewardsController {

    @Autowired
    private RewardsService rewardsService;

    @PostMapping("/fetch")
    private @ResponseBody FetchRewardsResponse fetchRewards(@RequestBody @Valid FetchRewardsRequest fetchRewardsRequest){
        return rewardsService.fetchRewards(fetchRewardsRequest);
    }
}
