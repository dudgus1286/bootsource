package com.example.jpa.entity;

import lombok.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TeamMember {
    @Column(name = "member_id")
    @Id
    private String id;

    private String userName;

    // sql의 외래키 제약조건 코드와 동일함
    @ManyToOne // TeamMember == Many , Team == One
    private Team team;
}
