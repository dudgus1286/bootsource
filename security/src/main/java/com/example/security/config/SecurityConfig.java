package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // 웹에서 security 적용 가능
@Configuration // == @Component(@Controller, @Service) : 환경설정을 담당하는 객체 생성
public class SecurityConfig {
    // 접근제한 개념
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 컨트롤러 접근 전 먼저 작동
        http
                // 요청 확인
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/security/guest", "/auth").permitAll()
                        .requestMatchers("/security/member").hasRole("USER")
                        .requestMatchers("/security/admin").hasRole("ADMIN"))
                // 인증 처리(웹에서는 대부분 폼 로그인 작업)
                // .formLogin(Customizer.withDefaults()); // default 로그인 페이지 보여주기
                .formLogin(login -> login.loginPage("/member/login").permitAll() // 커스텀 로그인 사용
                // .usernameParameter("userid") // 아이디(기본값: username) 파라메터 별도지정
                // .passwordParameter("pwd") // 비밀번호(기본값: password) 파라메터 별도지정
                // .successForwardUrl("/") // 로그인 성공 후 가야할 곳 지정
                )
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 커스텀 로그아웃
                        .logoutSuccessUrl("/") // default 로그인 페이지임
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // 임시 로그인용 계정 - 데이터베이스의 인증을 요청하는 객체
    // InMemoryUserDetailsManager - 메모리에 등록해 놓고 임시로 사용
    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder().username("user1")
                .password("{bcrypt}$2a$10$IUD3BefPUUMS0pvONKU6peLP/itj6GIocEnQ8VmBezTnlgWMHXAii")
                .roles("USER").build();
        UserDetails admin = User.builder().username("admin1")
                .password("{bcrypt}$2a$10$IUD3BefPUUMS0pvONKU6peLP/itj6GIocEnQ8VmBezTnlgWMHXAii")
                .roles("USER", "ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
