package com.subutai.customer.domain.sms.service;

import com.subutai.customer.domain.sms.entity.SmsSendHistory;
import com.subutai.customer.domain.sms.repository.SmsSendHistoryRepository;
import com.subutai.customer.dto.AccountKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(value = false)
@Slf4j
public class SmsSendHistoryServiceTest {

    @Autowired
    private SmsSendHistoryRepository smsSendHistoryRepository;

    @Autowired
    private SmsSendHistoryService smsSendHistoryService;


    @Test
    public void addTest() {
        String email = "sywon@ably.com";
        String phoneNumber = "010-1111-2222";
        int number = 12345;

        AccountKey accountKey = new AccountKey();
        accountKey.setEmail(email);
        accountKey.setPhoneNumber(phoneNumber);

        SmsSendHistory smsSendHistory = SmsSendHistory.insertRow(accountKey, number);

        smsSendHistoryRepository.save(smsSendHistory);

        Assert.assertEquals(smsSendHistory.getEmail(), smsSendHistoryRepository.findAllById(smsSendHistory.getId()).getEmail());
    }

}