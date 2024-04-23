package com.example.club.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.club.handle.ClubLoginSuccessHandler;

@EnableMethodSecurity // 컨트롤러의 @PreAuthorize 활성화
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http
                // 접근 제한 경로를 전부 작성하는 방법
                // .authorizeHttpRequests(authorize -> authorize
                // .requestMatchers("/", "/auth", "/static/**", "/img/*").permitAll()
                // .requestMatchers("/club/member").hasAnyRole("USER", "MANAGER", "ADMIN")
                // .requestMatchers("/club/manager").hasAnyRole("MANAGER")
                // .requestMatchers("/club/admin").hasAnyRole("ADMIN"))

                // 전체공개만 여기서 지정하고 권한이 필요한 그 외 페이지는 컨트롤러에서 작성
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/static/**", "/img/*").permitAll()
                        .anyRequest().permitAll())
                .formLogin(login -> login
                        .loginPage("/club/member/login").permitAll()
                        .successHandler(clubLoginSuccessHandler()))
                .oauth2Login(login -> login.successHandler(clubLoginSuccessHandler()))
                .rememberMe(remember -> remember.rememberMeServices(rememberMeServices))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/club/member/logout"))
                        .logoutSuccessUrl("/"));
        return http.build();
    }

    // 자동 로그인 처리 - 1) 쿠키 이용 2) 데이터베이스 이용
    // 쿠키 이용
    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256; // 로그인 정보를 암호화
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("myKey", userDetailsService,
                encodingAlgorithm); // 암호화한 로그인 정보를 "myKey"라는 이름으로 저장
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7); // 쿠키 만료 시간 지정
        return rememberMeServices;
    }

    // 암호화(encode), 비밀번호 입력값 검증(matches) : PasswordEncoder
    // 단방향 암호화 : 암호화만 가능, 복호화 불가능
    @Bean // 객체 생성
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    ClubLoginSuccessHandler clubLoginSuccessHandler() {
        return new ClubLoginSuccessHandler();
    }
}
