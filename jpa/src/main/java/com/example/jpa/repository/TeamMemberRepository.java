package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, String> {

    // sql 아님(객체(엔티티 클래스)를 기준으로 작성해야 함)
    @Query("select m,t from TeamMember m join m.team t where t.name=?1")
    // from TeamMember (다대일 관계에서 기준이 되는 객체인 팀멤버 테이블) m
    // join m.team (m(TeamMember)의 외래키 제약조건이 걸린 변수 team을 의미함) t
    // where t.name (Team의 이름) = ?1(첫번째 물음표 위치)
    public List<TeamMember> findByMemberEqualTeam(String teamName);

}
