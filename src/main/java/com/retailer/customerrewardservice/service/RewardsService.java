package com.retailer.customerrewardservice.service;


import com.retailer.customerrewardservice.exception.CustomerRewardException;
import com.retailer.customerrewardservice.dao.RewardsDao;
import com.retailer.customerrewardservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RewardsService {

    @Autowired
    private RewardsDao rewardsDao;

    /**
     * This method will fetch customer and transaction details and calls calculateRewrds and populates response.
     * @param fetchRewardsRequest
     * @return
     */
    public FetchRewardsResponse fetchRewards(FetchRewardsRequest fetchRewardsRequest) {
        FetchRewardsResponse fetchRewardsResponse = FetchRewardsResponse.builder().build();
        Optional<Customer> customerOptional = rewardsDao.fetchCustomerBasedOnCustomerId(fetchRewardsRequest.getCustomerId());
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            fetchRewardsResponse = FetchRewardsResponse.builder().customerId(customer.getId()).customerName(customer.getName()).build();
            List<Transaction> transactionList = rewardsDao.fetchTransactionsBasedOnCustomerIdAndDate(fetchRewardsRequest.getCustomerId(), fetchRewardsRequest.getFromDate(), fetchRewardsRequest.getToDate());
            if (!CollectionUtils.isEmpty(transactionList)) {
                return calculateAndPopulateRewardPoints(transactionList,fetchRewardsResponse);
            } else {
                //throw CustomerRewardException.builder().errorCode("1002").errorDescription("Reward Points Not Found").httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR).build();
                return fetchRewardsResponse;
            }
        } else {
            throw CustomerRewardException.builder().errorCode("1001").errorDescription("Customer Not Found").httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method performs below points:
     * 1. Group by Locale Month and Reward points of each month. -> Map<Month,Rewardpoints>.
     * 2. Stream Map via entrySet and build Rewards.
     * 3. sort based on month and collect rewards list.
     * @param transactionList
     * @param fetchRewardsResponse
     * @return
     */
    private FetchRewardsResponse calculateAndPopulateRewardPoints(List<Transaction> transactionList,FetchRewardsResponse fetchRewardsResponse) {
        List<Rewards> rewardsList = transactionList.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getTransactionDateTime().getMonth(),
                        Collectors.summingInt(RewardsService::calculateRewardPoints)))
                .entrySet().stream()
                .map(entry -> Rewards.builder().month(entry.getKey().getValue()).monthName(entry.getKey().name()).rewardPoints(entry.getValue()).build())
                .sorted(Comparator.comparing(rewards -> rewards.getMonth()))
                .collect(Collectors.toList());
        fetchRewardsResponse.setRewards(rewardsList);
        return fetchRewardsResponse;
    }

    private static int calculateRewardPoints(Transaction transaction) {
        double amount = transaction.getTransactionAmount();
        int points = 0;

        // Reward calculation based on the given logic
        if (amount > 100) {
            points += ((amount - 100) * 2); // 2 points per dollar over $100
            amount = 100;
        }
        if (amount > 50) {
            points += ((amount - 50) * 1); // 1 point per dollar between $50 and $100
        }
        return points;
    }

}
