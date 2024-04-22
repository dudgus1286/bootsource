package com.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {

    // SecurityConfig의 passwordEncoder()가 실행되면서 주입됨
    @Autowired
    private PasswordEncoder passwordEncoder;
    // 비밀번호 암호화(encode), 원 비밀번호와 암호화된 비밀번호의 일치 여부(.matches())

    @Test
    public void testEncoder() {
        String password = "1111"; // 원 비밀번호

        // 원 비밀번호의 암호화
        String encodePassword = passwordEncoder.encode(password);

        // password : 1111, encode password :
        // {bcrypt}$2a$10$IUD3BefPUUMS0pvONKU6peLP/itj6GIocEnQ8VmBezTnlgWMHXAii
        System.out.println("password : " + password + ", encode password : " + encodePassword);
        // matches(원비밀번호, 암호화된 비밀번호) = ture / 입력된 원 비밀번호를 또 암호화해서 암호화한 것들끼리 같은지 비교
        System.out.println(passwordEncoder.matches(password, encodePassword));
    }
}
