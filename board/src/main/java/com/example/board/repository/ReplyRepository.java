package com.example.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.board.entity.Reply;

import jakarta.transaction.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // bno 기준으로 reply 삭제 기능

    // @Query, FindBy~ 메소드는 기본적으로 select 명령용
    // @Query("delete from Reply r where r.Board.bno = :bno")
    @Modifying // delete, update 명령 시 필요한 어노테이션
    @Transactional
    @Query("delete from Reply r where r.board.bno = ?1 ")
    void deleteByBno(Long bno);

    @Query("select r from Reply r where r.board.bno = ?1")
    List<Reply> findByBno(Long bno);
}
