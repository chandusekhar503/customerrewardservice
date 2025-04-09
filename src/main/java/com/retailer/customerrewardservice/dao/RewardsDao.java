package com.retailer.customerrewardservice.dao;

import com.retailer.customerrewardservice.data.MockRewardsData;
import com.retailer.customerrewardservice.dto.Customer;
import com.retailer.customerrewardservice.dto.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RewardsDao {
    @Autowired
    private MockRewardsData mockRewardsData;

    /**
     * This method will fetch all transactions(mocked actual can be Database query)
     * and will filter transactions based on below 2 conditions.
     * 1. filter for particular customer transactions.
     * 2. then filter for txnDate falls between fromDate and toDate.
     * @param customerId
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<Transaction> fetchTransactionsBasedOnCustomerIdAndDate(String customerId, LocalDate fromDate, LocalDate toDate) {
     List<Transaction> transactionList = mockRewardsData.getTransactionList();
     return transactionList.stream()
             .filter(transaction -> transaction.getCustomerId().equalsIgnoreCase(customerId))
             .filter(transaction -> {
                 LocalDate txnDate = transaction.getTransactionDateTime().toLocalDate();
                 return !txnDate.isBefore(fromDate) && !txnDate.isAfter(toDate);
             }).collect(Collectors.toList());
    }

    public Optional<Customer> fetchCustomerBasedOnCustomerId(String customerId) {
        List<Customer> customersList = mockRewardsData.getCustomersList();
        return customersList.stream().filter(customer -> customer.getId().equalsIgnoreCase(customerId)).findFirst();
    }

}
