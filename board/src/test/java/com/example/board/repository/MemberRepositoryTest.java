package com.example.board.repository;

import static org.mockito.Mockito.mock;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.board.constant.MemberRole;
import com.example.board.entity.Member;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password("1111")
                    .name("USER" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void testInsert2() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@naver.com")
                    .password(passwordEncoder.encode("1111"))
                    .name("USER" + i)
                    .memberRole(MemberRole.MEMBER)
                    .build();
            memberRepository.save(member);
        });
    }

}
