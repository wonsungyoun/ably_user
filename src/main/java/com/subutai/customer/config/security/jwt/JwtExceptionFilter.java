package com.subutai.customer.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subutai.customer.result.ResponseData;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException e) {
            setErrorResponse(response,"잘못된 토큰 구조입니다.");
        } catch (ExpiredJwtException e) {
            setErrorResponse(response,"토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException e) {
            setErrorResponse(response,"jwt 형식에 맞지않습니다.");
        } catch (IllegalArgumentException e) {
            setErrorResponse(response,"타입이 맞지않습니다.");
        }
    }

    public void setErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(403);
        response.setContentType("application/json; charset=UTF-8");

        ResponseData responseData = ResponseData
                .builder()
                .code(403)
                .message(message)
                .build();

        response.getWriter().write(new ObjectMapper().writeValueAsString(responseData));
    }



}
