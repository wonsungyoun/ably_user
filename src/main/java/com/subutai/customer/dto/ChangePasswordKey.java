package com.subutai.customer.dto;

import lombok.Data;

@Data
public class ChangePasswordKey {
    private String email;
    private String password;
    private String newPassword;
}
