package com.subutai.customer.domain.customer.service;

import com.subutai.customer.domain.customer.entity.Customer;
import com.subutai.customer.domain.customer.repository.CustomerRepository;
import com.subutai.customer.domain.sms.service.SmsSendHistoryService;
import com.subutai.customer.dto.*;
import com.subutai.customer.exception.*;
import com.subutai.customer.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

/**
 * 회원 관련 서비스
 */
@Service
@Slf4j
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 로그인 정보 반환.
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = null;

        Customer customer = customerRepository.findCustomerByEmail(username);

        if(!ObjectUtils.isEmpty(customer)) {
            userDetails = customer.getLoginInfo();
        }

        return userDetails;
    }

    /**
     * 회원가입
     * @param registerData
     * @return
     */
    @Transactional
    public CustomerData register(RegisterData registerData) {

        // 기존 데이터가 존재하는지 확인
        Customer exCustomer = customerRepository.findCustomerByEmail(registerData.getEmail());

        // 존재 시 계정이 존재하므로 예외 처리.
        if(!ObjectUtils.isEmpty(exCustomer)) {
            throw new OverLapException("계정이 이미 존재합니다.");
        }

        // 비밀번호 암호화 처리
        registerData.setPassword(passwordEncoder.encode(registerData.getPassword()));

        Customer customer = Customer.register(registerData);
        customerRepository.save(customer);

        return customer.getCustomerData();
    }

    /**
     * 로그인
     * @param loginKey
     * @return
     */
    public UserDetails login(LoginKey loginKey) {
        Customer customer = customerRepository.findCustomerByEmail(loginKey.getEmail());

        // 계정이 존재 하지 않음.
        if(ObjectUtils.isEmpty(customer)) {
            throw new UserDetailsException("존재하지 않는 계정입니다.");
        }

        // 비밀번호 체크
        if(!passwordEncoder.matches(loginKey.getPassword(), customer.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 틀렸습니다.");
        }

        // 로그인 처리
        customer.login();
        customerRepository.save(customer);

        return customer.getLoginInfo();
    }

    /**
     * 회원정보 검색
     * @param email
     * @return
     */
    public CustomerData findCustomerDataByEmail(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email);

        // 계정이 존재하지 않음.
        if(ObjectUtils.isEmpty(customer)) {
            throw new AccountNotExistException("존재하지 않는 계정입니다.");
        }

        return customer.getCustomerData();
    }

    /**
     * 인증 후 일치시 패스워드 새로 생성.
     * @param regenerationKey
     * @return
     */
    public PasswordData passwordRegeneration(RegenerationKey regenerationKey) {
        Customer customer = customerRepository.findCustomerByEmail(regenerationKey.getEmail());

        // 비밀번호 랜덤으로 10자리 생성.
        String password = RandomUtils.randomPassword(10);

        // 비밀번호 변경.
        customer.changePassword(passwordEncoder.encode(password));
        customerRepository.save(customer);

        return new PasswordData(password); // 원래라면 이메일로 보내는 로직이 있어야 하지만 여기선 생략.
    }

    /**
     * 패스워드 변경.
     * @param changePasswordKey
     * @return
     */
    public void changePassword(ChangePasswordKey changePasswordKey)  {
        Customer customer = customerRepository.findCustomerByEmail(changePasswordKey.getEmail());

        // 스프링 시큐리티에서 한번 확인하지만 추가 적으로 확인하는 로직
        if(ObjectUtils.isEmpty(customer)) {
            throw new AccountNotExistException("계정이 존재하지 않습니다.");
        }


        if(!passwordEncoder.matches(changePasswordKey.getPassword(), customer.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 틀렸습니다.");
        }

        customer.changePassword(passwordEncoder.encode(changePasswordKey.getNewPassword()));
        customerRepository.save(customer);
    }

}
