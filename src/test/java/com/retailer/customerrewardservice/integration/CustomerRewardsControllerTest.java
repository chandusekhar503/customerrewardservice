package com.retailer.customerrewardservice.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailer.customerrewardservice.controller.CustomerRewardsController;
import com.retailer.customerrewardservice.dto.FetchRewardsRequest;
import com.retailer.customerrewardservice.dto.FetchRewardsResponse;
import com.retailer.customerrewardservice.dto.Rewards;
import com.retailer.customerrewardservice.service.RewardsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerRewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;  // To serialize the object

    @Test
    void test_FetchRewards_Customer_Not_Found() throws Exception {

        FetchRewardsRequest fetchRewardsRequest = FetchRewardsRequest.builder()
                .customerId("c12")
                .fromDate(LocalDate.of(2025,01,01))
                .toDate(LocalDate.of(2025,03,31))
                .build();
        MvcResult mvcResult = mockMvc.perform(post("/v1/api/rewards/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fetchRewardsRequest)))
                .andExpect(status().is5xxServerError())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(responseBody, Map.class);

        Assertions.assertNotNull(map);
        Assertions.assertEquals("Customer Not Found",map.get("errorMessage"));
        Assertions.assertEquals("1001",map.get("errorCode"));
    }

    @Test
    void test_FetchRewards_RewardPoints_Not_Found() throws Exception{
        FetchRewardsRequest fetchRewardsRequest = FetchRewardsRequest.builder()
                .customerId("c1")
                .fromDate(LocalDate.of(2024,01,01))
                .toDate(LocalDate.of(2024,03,31))
                .build();
        MvcResult mvcResult = mockMvc.perform(post("/v1/api/rewards/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fetchRewardsRequest)))
                .andExpect(status().isOk()).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        FetchRewardsResponse fetchRewardsResponse = objectMapper.readValue(responseBody, FetchRewardsResponse.class);

        Assertions.assertNotNull(fetchRewardsResponse);
        Assertions.assertEquals("chandra",fetchRewardsResponse.getCustomerName());
        Assertions.assertEquals("c1",fetchRewardsResponse.getCustomerId());
        List<Rewards> rewardsList = fetchRewardsResponse.getRewards();
        Assertions.assertNull(rewardsList);
    }

    @Test
    void test_FetchRewards_Success() throws Exception {
        FetchRewardsRequest fetchRewardsRequest = FetchRewardsRequest.builder()
                .customerId("c1")
                .fromDate(LocalDate.of(2025,01,01))
                .toDate(LocalDate.of(2025,03,31))
                .build();
       MvcResult mvcResult = mockMvc.perform(post("/v1/api/rewards/fetch")
                       .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fetchRewardsRequest)))
                .andExpect(status().isOk()).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        FetchRewardsResponse fetchRewardsResponse = objectMapper.readValue(responseBody, FetchRewardsResponse.class);

        Assertions.assertNotNull(fetchRewardsResponse);
        Assertions.assertEquals("chandra",fetchRewardsResponse.getCustomerName());
        Assertions.assertEquals("c1",fetchRewardsResponse.getCustomerId());
        List<Rewards> rewardsList = fetchRewardsResponse.getRewards();
        Assertions.assertNotNull(rewardsList);
        Assertions.assertEquals(3,rewardsList.size());

        Optional<Rewards> janRewardOpt = rewardsList.stream().filter(rewards -> rewards.getMonth() == 1).findFirst();
        Assertions.assertNotNull(janRewardOpt);
        Rewards janReward = janRewardOpt.get();
        Assertions.assertEquals(160,janReward.getRewardPoints());
        Assertions.assertEquals(1,janReward.getMonth());
        Assertions.assertEquals("JANUARY",janReward.getMonthName());

        Optional<Rewards> marchRewardOpt = rewardsList.stream().filter(rewards -> rewards.getMonth() == 3).findFirst();
        Assertions.assertNotNull(marchRewardOpt);
        Rewards marchReward = marchRewardOpt.get();
        Assertions.assertEquals(0,marchReward.getRewardPoints());
        Assertions.assertEquals(3,marchReward.getMonth());
        Assertions.assertEquals("MARCH",marchReward.getMonthName());


    }

    @Test
    void test_FetchRewards_NoCustomerIdRequest() throws Exception {
        FetchRewardsRequest fetchRewardsRequest = FetchRewardsRequest.builder()
                .fromDate(LocalDate.of(2025,01,01))
                .toDate(LocalDate.of(2025,03,31))
                .build();
        MvcResult mvcResult = mockMvc.perform(post("/v1/api/rewards/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fetchRewardsRequest)))
                .andExpect(status().is4xxClientError()).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(responseBody, Map.class);

        Assertions.assertNotNull(map);
        Assertions.assertEquals("Validation error",map.get("errorMessage"));
        Assertions.assertEquals("4001",map.get("errorCode"));
        Assertions.assertEquals("customerId should not be blank.",map.get("customerId"));

    }

    @Test
    void test_FetchRewards_NullFromDate() throws Exception {
        FetchRewardsRequest fetchRewardsRequest = FetchRewardsRequest.builder()
                .customerId("c1")
                .toDate(LocalDate.of(2025,03,31))
                .build();
        MvcResult mvcResult = mockMvc.perform(post("/v1/api/rewards/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fetchRewardsRequest)))
                .andExpect(status().is4xxClientError()).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(responseBody, Map.class);

        Assertions.assertNotNull(map);
        Assertions.assertEquals("Validation error",map.get("errorMessage"));
        Assertions.assertEquals("4001",map.get("errorCode"));
        Assertions.assertEquals("fromDate should not be empty.",map.get("fromDate"));
    }

    @Test
    void test_FetchRewards_NullToDate() throws Exception {
        FetchRewardsRequest fetchRewardsRequest = FetchRewardsRequest.builder()
                .fromDate(LocalDate.of(2025,01,01))
                .build();
        MvcResult mvcResult = mockMvc.perform(post("/v1/api/rewards/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fetchRewardsRequest)))
                .andExpect(status().is4xxClientError()).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        Map<String, Object> map = objectMapper.readValue(responseBody, Map.class);

        Assertions.assertNotNull(map);
        Assertions.assertEquals("Validation error",map.get("errorMessage"));
        Assertions.assertEquals("4001",map.get("errorCode"));
        Assertions.assertEquals("toDate should not be empty.",map.get("toDate"));
    }
}
