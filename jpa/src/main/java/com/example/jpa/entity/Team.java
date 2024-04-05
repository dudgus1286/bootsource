package com.example.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString(exclude = { "members" }) // ToString 할 때 members 값을 제외
// ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Team {
    @Column(name = "team_id")
    @Id
    private String id;

    @Column(name = "team_name")
    private String name;

    // 다대일 관계에서 일을 기준으로 다의 정보 조회
    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER) // 주인이 되는 연결점을 설정,
    // 기준이 되는 다(TeamMember)쪽의 기준(외래키 변수명(team)) 을 입력
    private List<TeamMember> members = new ArrayList<>();
}
