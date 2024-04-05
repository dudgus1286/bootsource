package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Locker;
import com.example.jpa.entity.SportsMember;

@SpringBootTest
public class LockerRepositoryTest {
    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    @Autowired
    private LockerRepository lockerRepository;

    @Test
    public void insertTest() {
        // Locker 삽입
        LongStream.range(1, 4).forEach(i -> {
            Locker locker = Locker.builder()
                    .name("locker" + i)
                    .build();
            lockerRepository.save(locker);
        });

        // SportsMember 삽입
        LongStream.range(1, 4).forEach(i -> {
            SportsMember member = SportsMember.builder()
                    .name("user" + i)
                    .locker(Locker.builder().id(i).build())
                    .build();
            sportsMemberRepository.save(member);
        });
    }

    @Test
    public void readTest() {
        // 회원정보 조회 후 locker 정보 조회
        SportsMember member = sportsMemberRepository.findById(1L).get();
        System.out.println(member);
        System.out.println(member.getLocker());
    }

}
