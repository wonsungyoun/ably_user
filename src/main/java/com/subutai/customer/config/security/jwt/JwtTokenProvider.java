package com.subutai.customer.config.security.jwt;

import com.subutai.customer.exception.TokenNotAccessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * 토근 관리 클래스
 */
@Component
@Slf4j
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private static final Long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000L; // 1시간으로 지정.

    private static final Long REFRESH_TOKEN_EXPIRE_TIME = 14 * 24 * 60 * 60 * 1000L; // 2주로 지정.

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    /**
     * access_token, refresh_token 생성
     * @param userDetails
     * @return
     */
    public TokenData createToken(UserDetails userDetails) {
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();

        String accessToken = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256 )
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();


        return TokenData.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * 토큰의 유효성 검사.
     * @param token
     * @return
     */
    public boolean validateToken(String token){
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return true;
    }

    /**
     * 토큰 복호화
     * @return
     */
    public Authentication getAuthentication(String accessToken) {
        Claims claims = this.parseClaims(accessToken);

        if( claims.get(AUTHORITIES_KEY) == null ) {
            throw new TokenNotAccessException("권한 정보가 없는 토큰입니다.");
        }
        Collection<? extends GrantedAuthority>  authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                   .map(SimpleGrantedAuthority::new)
                   .collect(Collectors.toList());

        // 유저정보 반환
        UserDetails user = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(user,"",authorities);
    }

    private Claims parseClaims(String accessToken) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        }catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
