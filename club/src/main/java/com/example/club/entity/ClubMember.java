package com.example.club.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.club.constant.ClubMemberRole;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ClubMember extends BaseEntity {
    @Id
    private String email;

    private String password;

    private String name;

    private boolean fromSocial; // 소셜가입

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ClubMemberRole> roleSet = new HashSet<>();
    // Set 사용 : 여러 값을 부여할 거지만 동일한 값은 중복되지 않게 하기 위해

    public void addMemberRole(ClubMemberRole memberRole) {
        roleSet.add(memberRole);
    }
}
