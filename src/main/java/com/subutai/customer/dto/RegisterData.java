package com.subutai.customer.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterData {

    @Email( message = "이메일 양식과 맞지 않습니다.")
    @NotBlank( message = "이메일을 입력하세요.")
    private String email;
    @NotBlank( message = "비밀번호를 입력하세요." )
    private String password;
    @NotBlank( message = "이름을 입력하세요." )
    private String name;
    @NotBlank( message = "닉네임을 입력하세요." )
    private String nickName;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다.")
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
