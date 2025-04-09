package com.retailer.customerrewardservice.integration;

import com.retailer.customerrewardservice.controller.CustomerRewardsController;
import com.retailer.customerrewardservice.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerRewardsController.class)
public class CustomerRewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    RewardsService rewardsService;

    @Test
    void test_FetchRewards_Customer_Not_Found(){

    }

    @Test
    void test_FetchRewards_RewardPoints_Not_Found(){

    }

    @Test
    void test_FetchRewards_Success(){

    }

    @Test
    void test_FetchRewards_NullCustomerIdRequest(){

    }

    @Test
    void test_FetchRewards_NullFromDate(){

    }

    @Test
    void test_FetchRewards_NullToDate(){

    }
}
