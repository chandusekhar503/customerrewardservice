package com.retailer.customerrewardservice.data;

import com.retailer.customerrewardservice.dto.Customer;
import com.retailer.customerrewardservice.dto.RewardsInfo;
import com.retailer.customerrewardservice.dto.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MockRewardsData {
    List<Transaction> transactionList =  new ArrayList<>();
    List<Customer> customersList =  new ArrayList<>();
    public MockRewardsData() {
        initializeMockData();
    }

    public List<Transaction> getTransactionList(){
        return transactionList;
    }

    public List<Customer> getCustomersList(){
        return customersList;
    }

    private void initializeMockData() {
        Customer c1= Customer.builder().name("chandra").id("c1").build();
        Customer c2= Customer.builder().name("sekhar").id("c2").build();
        customersList.add(c1);
        customersList.add(c2);

        Transaction t1 = Transaction.builder().customerId(c1.getId()).transactionId("T1001").transactionAmount(120).transactionDateTime(LocalDateTime.of(2025, 1, 1, 15, 30)).build();
        Transaction t2 = Transaction.builder().customerId(c1.getId()).transactionId("T1002").transactionAmount(110).transactionDateTime(LocalDateTime.of(2025, 1, 10, 9, 30)).build();
        Transaction t3 = Transaction.builder().customerId(c1.getId()).transactionId("T1003").transactionAmount(70).transactionDateTime(LocalDateTime.of(2025, 2, 4, 16, 30)).build();
        Transaction t4 = Transaction.builder().customerId(c1.getId()).transactionId("T1004").transactionAmount(40).transactionDateTime(LocalDateTime.of(2025, 3, 24, 18, 30)).build();


        Transaction t5 = Transaction.builder().customerId(c2.getId()).transactionId("T1005").transactionAmount(160).transactionDateTime(LocalDateTime.of(2025, 1, 1, 11, 30)).build();
        Transaction t6 = Transaction.builder().customerId(c2.getId()).transactionId("T1006").transactionAmount(10).transactionDateTime(LocalDateTime.of(2025, 2, 8, 12, 20)).build();
        Transaction t7 = Transaction.builder().customerId(c2.getId()).transactionId("T1007").transactionAmount(90).transactionDateTime(LocalDateTime.of(2025, 2, 12, 14, 30)).build();
        Transaction t8 = Transaction.builder().customerId(c2.getId()).transactionId("T1008").transactionAmount(220).transactionDateTime(LocalDateTime.of(2025, 3, 5, 19, 00)).build();

        transactionList.add(t1);
        transactionList.add(t2);
        transactionList.add(t3);
        transactionList.add(t4);
        transactionList.add(t5);
        transactionList.add(t6);
        transactionList.add(t7);
        transactionList.add(t8);

    }
}
