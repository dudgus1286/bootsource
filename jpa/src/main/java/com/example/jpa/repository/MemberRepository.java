package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Member;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {
    // select * from membertbl where name = '홍길동';
    List<Member> findByUserName(String userName);
}
