package com.example.jpa.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.RoleType;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTest() {
        IntStream.range(1, 11).forEach(i -> {
            Member member = Member.builder()
                    .id("ID " + i)
                    .userName("Member " + i)
                    .age(20 + i)
                    .roleType(RoleType.USER)
                    .createDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .description("description " + i)
                    .build();
            memberRepository.save(member);
        });
    };

    @Test
    public void readTest() {
        Member result = memberRepository.findById("ID 7").get();
        System.out.println(result);

        // 특정 이름으로 데이터 조회 (MemberRepository 인터페이스에서 메소드 먼저 설정)
        System.out.println(memberRepository.findByUserName("ID 4"));
    };

    @Test
    public void readAllTest() {
        List<Member> memberList = memberRepository.findAll();
        for (Member member : memberList) {
            System.out.println(member);
        }

    };

    @Test
    public void updateTest() {
        // Member member = memberRepository.findById("ID 7").get();
        // member.setRoleType(RoleType.ADMIN);
        // member.setDescription("Admin");
        // memberRepository.save(member);
        Optional<Member> result = memberRepository.findById("ID 6");
        result.ifPresent(member -> {
            member.setRoleType(RoleType.ADMIN);
            memberRepository.save(member);
        });
    };

    @Test
    public void deleteTest() {
        memberRepository.delete(memberRepository.findById("ID 9").get());
    };
}
