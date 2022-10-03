package com.subutai.customer.config.security.jwt;

import lombok.*;

@Getter
@NoArgsConstructor
public class TokenData {

    private String grantType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenData(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
