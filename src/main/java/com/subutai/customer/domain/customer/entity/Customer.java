package com.subutai.customer.domain.customer.entity;

import com.subutai.customer.dto.CustomerData;
import com.subutai.customer.dto.RegisterData;
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
    public Customer(String email, String password, String name, String nickName, String phoneNumber, Date regDateTime, String loginYn) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.regDateTime = regDateTime;
        this.loginYn = loginYn;
    }

    /**
     * 로그인 정보 반환.
     * @return
     */
    public UserDetails getLoginInfo() {
        return User.builder()
                    .username(this.email)
                    .password(this.password)
                    .roles("USER")
                .build();
    }

    /**
     * 회원 가입.
     * @param registerData
     * @return
     */
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

    /**
     * 정보 반환.
     * @return
     */
    public CustomerData getCustomerData() {
        return CustomerData.builder()
                .email(this.email)
                .name(this.name)
                .nickName(this.nickName)
                .phoneNumber(this.phoneNumber)
                .regDate(this.regDateTime)
                .lastLoginDate(this.lastLoginDate)
                .loginYn(this.loginYn)
                .build();
    }

    /**
     * 로그인 처리.
     */
    public void login() {
        this.lastLoginDate = new Date();
        this.loginYn = "Y";
    }

    /**
     * 비밀번호 변경
     */
    public void changePassword(String password){
        this.password = password;
    }
}
