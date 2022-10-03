package com.subutai.customer.domain.sms.controller;

import com.subutai.customer.domain.customer.service.CustomerService;
import com.subutai.customer.domain.sms.service.SmsSendHistoryService;
import com.subutai.customer.dto.AccountKey;
import com.subutai.customer.dto.CertificationNumberData;
import com.subutai.customer.dto.CustomerData;
import com.subutai.customer.exception.PasswordNotMatchException;
import com.subutai.customer.result.ResponseDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SmsSendHistoryController {

    private SmsSendHistoryService smsSendHistoryService;

    private CustomerService customerService;

    private ResponseDataFactory responseDataFactory;

    @Autowired
    public SmsSendHistoryController(SmsSendHistoryService smsSendHistoryService, CustomerService customerService, ResponseDataFactory responseDataFactory) {
        this.smsSendHistoryService = smsSendHistoryService;
        this.customerService = customerService;
        this.responseDataFactory = responseDataFactory;
    }

    /**
     * 휴대폰 문자 인증 (실제로 안나가고 나가는 로직 없음)
     * @param accountKey
     * @return
     */
    @PostMapping("/api/sms/create-certification-number")
    public ResponseEntity<CertificationNumberData> createCertificationNumber(@Valid @RequestBody AccountKey accountKey) {

        CustomerData customerData = customerService.findCustomerDataByEmail(accountKey.getEmail());

        if(!customerData.getPhoneNumber().equals(accountKey.getPhoneNumber())) {
            throw new PasswordNotMatchException("폰 번호가 일치하지 않습니다.");
        }

        return responseDataFactory.getSuccessResponseEntity(smsSendHistoryService.createNumber(accountKey));
    }

}
