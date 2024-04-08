package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString(exclude = "locker")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SportsMember extends BaseEntity {
    @SequenceGenerator(name = "sports_member_seq_gen", sequenceName = "sports_member_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sports_member_seq_gen")
    @Id
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String name;

    @OneToOne
    // @Column(name = "locker_id") // @OneToOne 과 같이 사용할 수 없음
    private Locker locker;
}
