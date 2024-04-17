package com.example.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "writer")
@Entity
public class Board extends BaseEntity {
    @SequenceGenerator(name = "board_seq_gen", sequenceName = "board_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_gen")
    @Id
    private Long bno;

    private String title;

    private String content;

    // @ManyToOne - 기본적으로 fetch 방식이 즉시 로딩(FetchType.EAGER)
    // EAGER : left join 방식으로 데이터 처리
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;
}
