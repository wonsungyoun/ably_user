package com.abley.customer.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginKey {
    private String email;
    private String password;

    @Builder
    public LoginKey(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
