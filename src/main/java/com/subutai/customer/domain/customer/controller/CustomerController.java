package com.subutai.customer.domain.customer.controller;

import com.subutai.customer.config.security.jwt.JwtTokenProvider;
import com.subutai.customer.config.security.jwt.TokenData;
import com.subutai.customer.domain.sms.service.SmsSendHistoryService;
import com.subutai.customer.dto.*;
import com.subutai.customer.exception.SmsSendHistoryNotExistException;
import com.subutai.customer.result.ResponseDataFactory;
import com.subutai.customer.domain.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 회원 관련 컨트롤러
 */
@RestController
public class CustomerController {

    private CustomerService customerService;

    private SmsSendHistoryService smsSendHistoryService;

    private ResponseDataFactory responseDataFactory;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public CustomerController(CustomerService customerService, SmsSendHistoryService smsSendHistoryService, ResponseDataFactory responseDataFactory, JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.smsSendHistoryService = smsSendHistoryService;
        this.responseDataFactory = responseDataFactory;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 회원 가입
     * @param registerData 회원 가입 데이터.
     * @return
     */
    @PostMapping ("/api/customer/register")
    public ResponseEntity<RegisterData> register(@Valid @RequestBody RegisterData registerData) {
        return responseDataFactory.getSuccessResponseEntity(customerService.register(registerData));
    }


    /**
     * 로그인
     * @param loginKey
     * @return
     */
    @PostMapping("/api/customer/login")
    public ResponseEntity<TokenData> login(@Valid @RequestBody LoginKey loginKey) {
        return responseDataFactory.getSuccessResponseEntity(jwtTokenProvider.createToken(customerService.login(loginKey))); // 반환
    }

    /**
     * 회원정보 확인 (로그인 시 이용 가능)
     * @param userDetails
     * @return
     */
    @GetMapping("/api/customer/info")
    public ResponseEntity<CustomerData> customerInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return responseDataFactory.getSuccessResponseEntity(customerService.findCustomerDataByEmail(userDetails.getUsername()));
    }

    /**
     * 비밀번호 초기화 후 재설정
     * @param regenerationKey
     * @return
     */
    @PostMapping("/api/customer/password-regeneration")
    public ResponseEntity<PasswordData> passwordRegeneration(@Valid @RequestBody RegenerationKey regenerationKey) {

        boolean isCertification = smsSendHistoryService.certification(regenerationKey);

        if(!isCertification) {
            throw new SmsSendHistoryNotExistException("인증 시간이 만료되었습니다.");
        }

        return responseDataFactory.getSuccessResponseEntity(customerService.passwordRegeneration(regenerationKey));
    }

    /**
     * 비밀번호 변경 (로그인 시 이용 가능)
     * @param changePasswordKey
     * @return
     */
    @PostMapping("/api/customer/change-password")
    public ResponseEntity changePassword(@Valid @RequestBody ChangePasswordKey changePasswordKey) {
        customerService.changePassword(changePasswordKey);
        return responseDataFactory.getSuccessResponseEntity("ok");
    }
}
