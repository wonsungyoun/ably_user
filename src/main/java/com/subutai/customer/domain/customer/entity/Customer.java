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

    // 이메일
    @Id
    private String email;

    // 비밀번호
    @JsonIgnore
    private String password;

    // 이름
    private String name;

    // 닉네임
    @Column(name = "nick_name")
    private String nickName;

    // 폰번호
    @Column(name = "phone_number")
    private String phoneNumber;

    // 등록날짜
    @Column(name="reg_date_time")
    private Date regDateTime;

    // 마지막 로그인 시각
    @Column(name = "last_login_date_time")
    private Date lastLoginDate;

    // 현재 로그인 여부 (이 프로젝트에서는 비중없는 컬럼이지만, 실제 이커머스페이지에선 필요)
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
