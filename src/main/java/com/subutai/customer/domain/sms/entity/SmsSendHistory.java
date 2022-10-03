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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "certification_number")
    private int certificationNumber;

    @Column(name="send_date_time")
    private Date sendDate;

    @Builder
    public SmsSendHistory(Long id,String email, String phoneNumber, int certificationNumber, Date sendDate) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.certificationNumber = certificationNumber;
        this.sendDate = sendDate;
    }

    public static SmsSendHistory insertRow(AccountKey accountKey, int certificationNumber) {
        return SmsSendHistory.builder()
                .email(accountKey.getEmail())
                .phoneNumber(accountKey.getPhoneNumber())
                .certificationNumber(certificationNumber)
                .sendDate(new Date())
                .build();
    }


}
