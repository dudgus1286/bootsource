package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "memotbl")
@Entity // 데이터베이스에서 데이터로 관리하는 대상
public class Memo {
    // GenerationType.AUTO : create sequence memotbl_seq start with 1 increment by
    // 50 (1에서 바로 50)
    // IDENTITY :create table ~(생략)~ mno number(19,0) generated as identity, ~(생략)
    // (기본키 생성은 데이터 베이스에 위임)
    // SEQUENCE : 시퀀스 설정하지 않으면 AUTO 와 동일
    @SequenceGenerator(name = "memo_seq_gen", sequenceName = "memo_seq", allocationSize = 1) // 시퀀스 설정
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memo_seq_gen") // 시퀀스 생성
    @Id // == Primary Key
    private Long mno;

    // 자바 변수명은 _(언더바) 사용 안 함, memoText(자바) => memo_text(데이터베이스의 컬럼)
    @Column(nullable = false, length = 300) // not null , 데이터 크기 지정
    private String memoText;
    /*
     * Hibernate:
     * create table memotbl (
     * mno number(19,0) not null,
     * memo_text varchar2(255 char),
     * primary key (mno)
     * )
     */
}
