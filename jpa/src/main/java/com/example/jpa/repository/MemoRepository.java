package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Memo;

// JpaRepository<엔티티명, 기본키의타입> 상속
public interface MemoRepository extends JpaRepository<Memo, Long> {
    // DAO 역할
}
