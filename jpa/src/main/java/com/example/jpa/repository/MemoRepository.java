package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Memo;

// JpaRepository<엔티티명, 기본키의타입> 상속
public interface MemoRepository extends JpaRepository<Memo, Long> {
    // DAO 역할

    // mno 가 5 보다 작은 메모 조회
    List<Memo> findByMnoLessThan(Long mno);

    // mno 가 10 보다 작은 메모 조회 (mno 기준 내림차순)
    List<Memo> findByMnoLessThanOrderByMnoDesc(Long mno);

    // mno 가 50 이상 70 이하
    List<Memo> findByMnoGreaterThanEqualAndMnoLessThanEqual(Long greaterNum, Long lessNum);

    List<Memo> findByMnoBetween(Long start, Long end);

}
