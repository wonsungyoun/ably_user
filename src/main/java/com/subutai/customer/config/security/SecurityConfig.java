package com.subutai.customer.config.security;

import com.subutai.customer.config.security.jwt.JwtAuthenticationFilter;
import com.subutai.customer.config.security.jwt.JwtTokenProvider;
import com.subutai.customer.domain.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * 스프링 시큐리티 설정
 */
@Configuration
@EnableWebSecurity // 스프링 시큐리티 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true) // 특정 어노테이션 사용
@Slf4j
public class SecurityConfig {

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

    @Bean
    public BCrypt bCrypt() {return new BCrypt();};

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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

    /**
     * js, images 등 허용 설정 (h2-console 사용시 필요)
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        this.setHeaders(http); // 헤더 설정.
        //this.setAuthorizeHttpRequests(http);

        http
                .httpBasic().disable() // 기본 설정 해제
                .csrf().disable() // jwt토큰을 사용하므로 보안 토큰 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt토큰을 사용하므로 세션 미사용
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/api/customer/register","/api/customer/login", "/api/customer/password-regeneration","/h2/**","/h2-console/**", "/api/sms/create-certification-number").permitAll() // 해당 url만 전체 사용 가능
                .anyRequest().authenticated() // 다른 url은 로그인 필요
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt 토큰 체크를 최우선으로 한다.
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

    private void setSessionManagement(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 미사용.
//                .maximumSessions(1) // 최대 허용 세션
//                .maxSessionsPreventsLogin(false); // true 일시 허용 세션 초과시 로그인 차단, false 일시 기존 세션 만료.
    }

}
