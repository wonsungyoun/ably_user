package com.subutai.customer.dto;

import lombok.Getter;

/**
 *  인증 번호
 */
@Getter
public class CertificationNumberData {
    private int number;

    public CertificationNumberData(int number) {
        this.number = number;
    }
}
