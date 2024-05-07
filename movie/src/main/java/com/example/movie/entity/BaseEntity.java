package com.example.movie.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EntityListeners(value = AuditingEntityListener.class)
@MappedSuperclass // BaseEntity 상속할 경우 필드를 컬럼으로 인식하기
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false) // 수정 시 값이 null 이 되는 걸 방지
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
