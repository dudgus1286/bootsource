package com.example.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.movie.handler.CustomAccessDeniedHandler;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/assets/**", "/css/**", "/js/**", "/auth").permitAll()
                .requestMatchers("/movie/list", "/movie/read").permitAll()
                .requestMatchers("/movie/modify").hasRole("ADMIN")
                .requestMatchers("/upload/display").permitAll()
                .requestMatchers("/reviews/**").permitAll()
                .requestMatchers("/member/register").permitAll()
                .anyRequest().authenticated());
        // login 페이지는 /member/login 경로로 요청
        // login 성공 후 이동경로는 시작했던 곳으로 가는 게 기본
        http.formLogin(login -> login
                .loginPage("/member/login").permitAll()
                .defaultSuccessUrl("/movie/list", true));

        // http.csrf(csrf -> csrf.disable()); // csrf 필터 비활성화
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/"));

        // 403(접근제한) - 정적 페이지와 연결
        // http.exceptionHandling(exception ->
        // exception.accessDeniedPage("/access-denied.html"));
        http.exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler()));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}
