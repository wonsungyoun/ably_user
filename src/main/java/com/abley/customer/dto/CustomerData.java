package com.abley.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 사용자 정보 반환 데이타
 */
@Data
@Builder
@ToString
public class CustomerData {

    // 이메일
    private String email;

    // 이름
    private String name;

    // 유저 별칭
    private String nickName;

    // 번호
    private String phoneNumber;

    // 등록날짜
    private Date regDate;

    // 마지막 로그인 날짜
    private Date lastLoginDate;

    // 로그인 유무
    private String loginYn;
}
