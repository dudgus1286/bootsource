package com.example.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@ToString(exclude = "child")
public class Parent {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    // mappedBy = "상대편의 외래키 달린 컬럼 명"-FetchType.LAZY (기본값)
    // cascade : 영속상태 전이
    // 부모가 영속상태일 시 자식도 같이 영속상태로 감
    // orphanRemoval : 부모하고 연결이 끊긴 고아객체 자동삭제 여부
    private List<Child> child = new ArrayList<>();
}
