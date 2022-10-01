package com.abley.customer.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class RegisterData {

    private String email;
    private String password;
    private String name;
    private String nickName;
    private String phoneNumber;

    @Builder
    public RegisterData(String email, String password, String name, String nickName, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
    }
}
