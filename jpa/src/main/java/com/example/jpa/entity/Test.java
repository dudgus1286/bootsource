package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// 기본: int, long, boolean, char, float, double : null 대입 불가 (NOT NULL)
// 객체: Integer, Long, Boolean .... : null 대입 가능

// Long, long : NUMBER(19,0)
// Integer, int : NUMBER(10,0)

@Entity
public class Test {

    @Id
    private Long id;

    @Column(columnDefinition = "number(8)") // 컬럼의 속성 직접지정 가능
    private long id2;
    private int id3;
    private Integer id4;

}
