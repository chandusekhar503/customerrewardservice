package com.retailer.customerrewardservice.unit;


import com.retailer.customerrewardservice.dao.RewardsDao;
import com.retailer.customerrewardservice.dto.*;
import com.retailer.customerrewardservice.exception.CustomerRewardException;
import com.retailer.customerrewardservice.service.RewardsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {

    @Mock
    private RewardsDao rewardsDao;
    @InjectMocks
    private RewardsService rewardsService;

    @Test
    void testfetchRewards_CustomerNotFound(){
        Mockito.when(rewardsDao.fetchCustomerBasedOnCustomerId("c1")).thenReturn(Optional.empty());
        FetchRewardsRequest fetchRewardsRequest = FetchRewardsRequest.builder().customerId("c1").fromDate(LocalDate.now()).toDate(LocalDate.now()).build();
        CustomerRewardException customerRewardException = Assertions.assertThrows(CustomerRewardException.class,() ->{
            rewardsService.fetchRewards(fetchRewardsRequest);
        });
        Assertions.assertEquals("1001",customerRewardException.getErrorCode());
        Assertions.assertEquals("Customer Not Found",customerRewardException.getErrorDescription());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,customerRewardException.getHttpStatusCode());

    }

    @Test
    void testfetchRewards_RewardsNotFound() {
        Customer c1 = Customer.builder().name("chandra").id("c1").build();
        Mockito.when(rewardsDao.fetchCustomerBasedOnCustomerId("c1")).thenReturn(Optional.of(c1));
        Mockito.when(rewardsDao.fetchTransactionsBasedOnCustomerIdAndDate("c1",LocalDate.now(),LocalDate.now())).thenReturn(null);
        FetchRewardsRequest fetchRewardsRequest = FetchRewardsRequest.builder().customerId("c1").fromDate(LocalDate.now()).toDate(LocalDate.now()).build();
        FetchRewardsResponse fetchRewardsResponse =  rewardsService.fetchRewards(fetchRewardsRequest);

        Assertions.assertEquals("chandra",fetchRewardsResponse.getCustomerName());
        Assertions.assertEquals("c1",fetchRewardsResponse.getCustomerId());
        Assertions.assertEquals(null,fetchRewardsResponse.getRewards());
    }

    @Test
    void testfetchRewards_Success(){
        Customer c1 = Customer.builder().name("chandra").id("c1").build();

        List<Transaction> transactionList =  new ArrayList<>();
        Transaction t1 = Transaction.builder().customerId(c1.getId()).transactionId("T1001").transactionAmount(120).transactionDateTime(LocalDateTime.of(2025, 1, 1, 15, 30)).build();
        Transaction t2 = Transaction.builder().customerId(c1.getId()).transactionId("T1002").transactionAmount(110).transactionDateTime(LocalDateTime.of(2025, 1, 10, 9, 30)).build();
        Transaction t3 = Transaction.builder().customerId(c1.getId()).transactionId("T1003").transactionAmount(70).transactionDateTime(LocalDateTime.of(2025, 2, 4, 16, 30)).build();
        Transaction t4 = Transaction.builder().customerId(c1.getId()).transactionId("T1004").transactionAmount(40).transactionDateTime(LocalDateTime.of(2025, 3, 24, 18, 30)).build();

        transactionList.add(t1);
        transactionList.add(t2);
        transactionList.add(t3);
        transactionList.add(t4);

        Mockito.when(rewardsDao.fetchCustomerBasedOnCustomerId("c1")).thenReturn(Optional.of(c1));
        Mockito.when(rewardsDao.fetchTransactionsBasedOnCustomerIdAndDate("c1",LocalDate.now(),LocalDate.now())).thenReturn(transactionList);


        FetchRewardsRequest fetchRewardsRequest = FetchRewardsRequest.builder().customerId("c1").fromDate(LocalDate.now()).toDate(LocalDate.now()).build();
        FetchRewardsResponse fetchRewardsResponse =  rewardsService.fetchRewards(fetchRewardsRequest);

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
}
