package com.abley.customer.entity;

import com.abley.customer.dto.CustomerData;
import com.abley.customer.dto.RegisterData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * customer table entity
 */
@Entity
@Getter
@NoArgsConstructor
public class Customer {
    @Id
    private String email;

    @JsonIgnore
    private String password;

    private String name;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name="reg_date_time")
    private Date regDateTime;

    @Column(name = "last_login_date_time")
    private Date lastLoginDate;
    @Column(name = "login_yn")
    private String loginYn;

    @Builder
    public Customer(String email, String password, String name, String nickName, String phoneNumber, Date regDateTime, Date lastLoginDate, String loginYn) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.regDateTime = regDateTime;
        this.lastLoginDate = lastLoginDate;
        this.loginYn = loginYn;
    }

    public UserDetails getLoginInfo() {
        return User.builder()
                    .username(this.email)
                    .password(this.password)
                    .roles("USER")
                .build();
    }

    public static Customer register(RegisterData registerData) {
        return Customer
                .builder()
                    .email(registerData.getEmail())
                    .password(registerData.getPassword())
                    .name(registerData.getName())
                    .nickName(registerData.getNickName())
                    .phoneNumber(registerData.getPhoneNumber())
                    .regDateTime(new Date())
                    .loginYn("N")
                .build();
    }

    public CustomerData getCustomerData() {
        return CustomerData.builder()
                .email(this.email)
                .name(this.name)
                .nickName(this.nickName)
                .phoneNumber(this.phoneNumber)
                .regDate(this.regDateTime)
                .loginYn(this.loginYn)
                .build();
    }

}
