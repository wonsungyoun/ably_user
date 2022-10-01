package com.abley.customer.service;

import com.abley.customer.dto.CustomerData;
import com.abley.customer.dto.RegisterData;
import com.abley.customer.entity.Customer;
import com.abley.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

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

    @Transactional
    public CustomerData register(RegisterData registerData) {
        registerData.setPassword(passwordEncoder.encode(registerData.getPassword()));

        Customer customer = Customer.register(registerData);
        customerRepository.save(customer);

        return customer.getCustomerData();
    }
}
