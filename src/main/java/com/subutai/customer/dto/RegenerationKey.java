package com.subutai.customer.dto;

import lombok.Data;

@Data
public class RegenerationKey {
    private String email;
    private String phoneNumber;
    private int certificationNumber;
}
