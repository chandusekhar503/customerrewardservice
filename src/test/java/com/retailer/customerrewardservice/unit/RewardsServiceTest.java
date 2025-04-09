package com.retailer.customerrewardservice.unit;


import com.retailer.customerrewardservice.dao.RewardsDao;
import com.retailer.customerrewardservice.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {

    @Mock
    private RewardsDao rewardsDao;
    @InjectMocks
    private RewardsService rewardsService;

    @Test
    void testfetchRewards_CustomerNotFound(){

    }

    @Test
    void testfetchRewards_RewardsNotFound(){

    }

    @Test
    void testfetchRewards_Success(){

    }
}
