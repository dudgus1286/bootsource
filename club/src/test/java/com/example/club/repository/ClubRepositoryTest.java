package com.example.club.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.club.constant.ClubMemberRole;
import com.example.club.entity.ClubMember;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ClubRepositoryTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Test
    public void testInsert() {
        // 1 ~ 80 : USER, 81 ~ 90 : USER, MANAGER, 91 ~ 100 : USER, MANAGER, ADMIN
        IntStream.rangeClosed(1, 100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user" + i + "@soldesk.co.kr")
                    .name("사용자" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111")) // 단방향 암호화
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);

            if (i > 80) {
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }

            if (i > 90) {
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }
            clubMemberRepository.save(clubMember);
        });
    }

    // FetchType
    // EAGER : left outer join 기본으로 실행
    // LAZY : select 두 번으로 처리

    // 웹 개발 시 EAGER 를 자주 사용하지 말자 => 처음부터 사용하지 않는 정보를 가지고 나올 필요가 없기 때문

    // @OneToOne, @ManyToOne : 기본적으로 FetchType.EAGER 인 것들이니 변경
    @Test
    public void testFind() {
        ClubMember member = clubMemberRepository.findByEmailAndFromSocial("user95@soldesk.co.kr", false).get();
        System.out.println(member);
    }
}
