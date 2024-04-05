package com.example.jpa.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;

@SpringBootTest
public class TeamRepositoryTest {
    // 여기서는 final 못 씀
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTeamTest() {
        // 팀 정보 삽입
        Team team1 = teamRepository.save(Team.builder().id("team1").name("팀1").build());
        Team team2 = teamRepository.save(Team.builder().id("team2").name("팀2").build());
        Team team3 = teamRepository.save(Team.builder().id("team3").name("팀3").build());

        teamMemberRepository.save(TeamMember.builder().id("member1").userName("홍길동").team(team1).build());
        teamMemberRepository.save(TeamMember.builder().id("member2").userName("성춘향").team(team2).build());
        teamMemberRepository.save(TeamMember.builder().id("member3").userName("이순신").team(team2).build());
        teamMemberRepository.save(TeamMember.builder().id("member4").userName("정우성").team(team3).build());
    }

    // 연관관계가 있는 데이터 조회
    // 1. 다대일(N:1) 관계
    // 기본적으로 N을 조회해도 1에 해당하는 엔티티 정보를 가지고 나옴 (left join으로) => FetchType.EAGER(기본값)
    // ==> 팀과 다대일(멤버:팀) 관계인 멤버 정보 조회 시 팀 정보도 같이 조회됨 (객체 그래프 탐색으로 접근 가능)
    // ==> 객체지향 쿼리 작성(@Query) (!=SQL)
    // 2. 일대다(1:N) 관계
    // 관계에서 N에 해당하는 엔티티를 안 가지고 나옴 => FetchType.LAZY(기본값)

    // FetchType : 연결관계에서 상대 정보를 같이 가지고 나올건지 말건지의 여부
    // FetchType.EAGER : 가지고 나옴
    // FetchType.LAZY : 안 가지고 나옴

    @Test
    public void getRowTest() {
        // 멤버의 정보 조회하기
        // TeamMember 테이블(N) : Team 테이블(1) => 외래키 제약조건이 걸려있음
        // 이러면 teamMember 찾을 때 N:1 관계에서는 1에 해당하는 정보도 기본적으로 가지고 옴
        // ==> join 필요 (left join)
        TeamMember teamMember = teamMemberRepository.findById("member1").get();
        /*
         * select
         * tm1_0.member_id,
         * t1_0.team_id,
         * t1_0.team_name,
         * tm1_0.user_name
         * from
         * team_member tm1_0
         * left join // <===
         * team t1_0
         * on t1_0.team_id=tm1_0.team_team_id
         * where
         * tm1_0.member_id=?
         */
        System.out.println(teamMember); // TeamMember(id=member1, userName=홍길동, team=Team(id=team1, name=팀1))
        // 객체 그래프 탐색
        System.out.println("팀 전체 정보: " + teamMember.getTeam()); // Team(id=team1, name=팀1)
        System.out.println("팀 명: " + teamMember.getTeam().getName()); // 팀1

        // 회원 조회 시 나와 같은 팀에 소속된 회원 모두 조회
        teamMemberRepository.findByMemberEqualTeam("팀2").forEach(member -> {
            System.out.println(member);
            /*
             * TeamMember(id=member2, userName=성춘향, team=Team(id=team2, name=팀2))
             * TeamMember(id=member3, userName=이순신, team=Team(id=team2, name=팀2))
             */
        });
    }

    @Test
    public void updateTest() {
        // 멤버의 팀 변경
        // 수정할 회원 조회
        TeamMember member = teamMemberRepository.findById("member3").get();
        // 회원의 새 소속팀 값 생성(이미 있는 팀 아이디)
        Team team = Team.builder().id("team3").build();
        // 팀 변경
        member.setTeam(team);

        System.out.println("수정 후 " + teamMemberRepository.save(member));
        // 수정 후 TeamMember(id=member3, userName=이순신, team=Team(id=team3, name=팀3))

    }

    @Test
    public void deleteTest() {
        // 팀원 삭제 먼저 or 팀원의 팀을 null로 설정
        TeamMember member = teamMemberRepository.findById("member1").get();
        member.setTeam(null);
        teamMemberRepository.save(member);

        // 팀 삭제
        teamRepository.deleteById("team1");
        // 팀 먼저 삭제하려고 할 떄 :
        // 무결성 제약조건(C##JPAUSER.FK6EVBWSGG1I7QQ2B1ABGC4RJJA)이 위배되었습니다- 자식 레코드가 발견되었습니다

    }

    // OneToMany 설정해서 팀을 기준으로 회원 조회 가능한가?
    // @Transactional
    @Test
    public void getMemberList() {
        Team team = teamRepository.findById("team3").get();
        System.out.println(team);
        // org.hibernate.LazyInitializationException
        // @Transactional 가 없을 때 오류가 난 이유:
        // 1) ToString() 메소드가 members 변수 출력하려고 했기 때문
        // 2) team 값 불려올 때 실행된 members 변수의 OneToMany 어노테이션 작업은 이미 끝났고 이후 다음 작업에서 없는 정보를
        // 불려내려고 해서

        // 해결방법 1 : 메소드에 @Transactional 어노테이션 추가
        // 기존에는 개별로 명령이 실행됨. 그래서 멤버변수의 @OneToMany 실행은 team 정보를 불려들었을때 끝남
        // 그래서 팀멤버 반복 출력 명령 때 정보가 없어서 오류가 남
        // 트랜잭션으로 명령을 전부 묶고 한꺼번에 실행해서 명령의 결과를 다른 명령에서 이어서 할 수 있음.

        // 2: 일대다 멤버변수의 fetch를 FetchType.EAGER 로 변경
        // members 멤버변수의 fetch 값을 변경함

        team.getMembers().forEach(member -> System.out.println(member));
        // select t1_0.team_id, t1_0.team_name from team t1_0 where t1_0.team_id=?
        // Team(id=team3, name=팀3)
        // select m1_0.team_team_id, m1_0.member_id, m1_0.user_name from team_member
        // m1_0 where m1_0.team_team_id=?
        // TeamMember(id=member3, userName=이순신, team=Team(id=team3, name=팀3))
        // TeamMember(id=member4, userName=정우성, team=Team(id=team3, name=팀3))

    }
}
