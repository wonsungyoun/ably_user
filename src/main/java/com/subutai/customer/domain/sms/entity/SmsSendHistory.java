package com.subutai.customer.domain.sms.entity;

import com.subutai.customer.dto.AccountKey;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Slf4j
public class SmsSendHistory {

    // 시퀀스
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 전송자 이메일
    private String email;

    // 전송자 폰번호
    @Column(name = "phone_number")
    private String phoneNumber;

    // 인증번호
    @Column(name = "certification_number")
    private int certificationNumber;

    // 전송날짜
    @Column(name="send_date_time")
    private Date sendDate;

    // 전송확인
    @Column(name="is_confirm")
    private String isConfirm;

    @Builder
    public SmsSendHistory(Long id,String email, String phoneNumber, int certificationNumber, Date sendDate, String isConfirm) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.certificationNumber = certificationNumber;
        this.sendDate = sendDate;
        this.isConfirm = isConfirm;
    }

    public static SmsSendHistory insertRow(AccountKey accountKey, int certificationNumber) {
        return SmsSendHistory.builder()
                .email(accountKey.getEmail())
                .phoneNumber(accountKey.getPhoneNumber())
                .certificationNumber(certificationNumber)
                .sendDate(new Date())
                .isConfirm("N")
                .build();
    }

    /**
     * 인증 확인 체크
     */
    public void confirm() {
        this.isConfirm = "Y";
    }


}
