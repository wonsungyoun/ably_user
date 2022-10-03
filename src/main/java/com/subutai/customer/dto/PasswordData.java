package com.subutai.customer.dto;

import lombok.Data;

@Data
public class PasswordData {
    private String password;

    public PasswordData(String password) {
        this.password = password;
    }
}
