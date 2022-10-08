package com.subutai.customer.domain.customer.service;

import com.subutai.customer.dto.RegisterData;
import com.subutai.customer.domain.customer.entity.Customer;
import com.subutai.customer.domain.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(value = false)
public class CustomerServiceTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @DisplayName("계정 테스트")
    @Test
    public void addTest() {
        String email = "sywon@subutai.com";
        String password = "subutai123!";
        String name = "원성연";
        String nicKName = "수부타이";
        String phoneNumber = "010-1111-2222";

        RegisterData registerData = RegisterData.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickName(nicKName)
                .phoneNumber(phoneNumber)
                .build();

        Customer customer = Customer.register(registerData);

        customerRepository.save(customer);

        Assert.assertEquals(customer.getCustomerData().getEmail(), customerRepository.findCustomerByEmail(email).getCustomerData().getEmail());

    }

    @DisplayName("회원가입 로직 테스트")
    @Test
    public void registerTest() {
        String email = "sywon@subutai.com";
        String password = "subutai123!";
        String name = "원성연";
        String nicKName = "수부타이";
        String phoneNumber = "010-1111-2222";

        RegisterData registerData = RegisterData.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickName(nicKName)
                .phoneNumber(phoneNumber)
                .build();

        Assert.assertEquals(customerService.register(registerData).getEmail(), customerRepository.findCustomerByEmail(email).getCustomerData().getEmail());
    }

}