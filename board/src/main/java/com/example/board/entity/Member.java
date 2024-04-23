package com.example.board.entity;

import com.example.board.constant.MemberRole;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Member extends BaseEntity {
    @Id
    private String email;

    private String password;

    private String name;

    private MemberRole memberRole;
}
