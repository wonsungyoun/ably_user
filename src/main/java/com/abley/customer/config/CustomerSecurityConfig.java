package com.abley.customer.config;

import com.abley.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * 스프링 시큐리티 설정
 */
@Configuration
@EnableWebSecurity // 스프링 시큐리티 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true) // 특정 어노테이션 사용
@Slf4j
public class CustomerSecurityConfig {

    /**
     * 계정 서비스 관련 객체 설정
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomerService();
    }

    /**
     * 비밀번호 인코더 관련 객체 설정
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 인증 유효성 판단 객체 생성
     * @param http
     * @param bCryptPasswordEncoder
     * @param userDetailsService
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        this.setHeaders(http); // 헤더 설정.
        this.setAuthorizeHttpRequests(http);


        return http.build();
    }

    /**
     * 인증 정보 초기화
     * @return
     */
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    /**
     * 헤더 설정
     * @param http
     * @throws Exception
     */
    private void setHeaders(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions()// 클릭재킹 공격 방지.
                .sameOrigin(); // 같은 도메인만 참조.
    }

    /**
     * 특정 url 경로 비로그인 허용
     */
    private void setAuthorizeHttpRequests(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/api/customer/register","/api/customer/find-password","/h2/**","/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();

    }

    private void setSessionManagement(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .maximumSessions(1) // 최대 허용 세션
                .maxSessionsPreventsLogin(false); // true 일시 허용 세션 초과시 로그인 차단, false 일시 기존 세션 만료.
    }

}
