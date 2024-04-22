package com.example.club.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.club.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
    // 회원 찾기 (email, social 회원여부)
    @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraphType.LOAD)
    // @EntityGraph(): 이 메소드 작업을 할 때만 roleSet을 불러오도록 지정함
    @Query("select m from ClubMember m where m.email = :email and m.fromSocial = :social")
    Optional<ClubMember> findByEmailAndFromSocial(String email, boolean social);
}
