package com.example.todo.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamicInsert // default 동작시키기 위해 추가
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "todotbl")
@Entity
public class Todo extends BaseEntity {
    @SequenceGenerator(name = "todo_seq_gen", sequenceName = "todo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_seq_gen")
    @Id
    @Column(name = "todo_id")
    private Long id;

    // @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean completed;
    // completed number(1,0) default 0 not null check (completed in (0,1))
    // 데이터타입 숫자, not null, check(completed in (0,1)) 두 개의 제약 조건

    // @Column(nullable = false)
    @ColumnDefault("0") // SQL 구문에서 default 값을 설정하는 것과 같음
    private Boolean important;
    // 아무것도 입력되지 않으면 default 값으로 입력하라는 의미
    // JPA 에서는 디폴트 값으로 자동삽입하려면 @DinamicInsert 가 필요
    // @DinamicInsert : 데이터가 존재하는 필드만으로 insert SQL문 생성(not null 이 아닌 필드만 해줌)

    @Column(nullable = false)
    private String title;

}
