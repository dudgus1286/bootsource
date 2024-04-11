package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // id로 찾기
    // title로 찾기 (=)
    List<Board> findByTitle(String title);

    // title로 찾기 (like)
    List<Board> findByTitleLike(String title);

    List<Board> findByTitleStartingWith(String title);

    List<Board> findByTitleEndingWith(String title);

    List<Board> findByTitleContaining(String title);

    // writer로 찾기
    List<Board> findByWriter(String writer);

    // writer가 user 로 시작하는 작성자 찾기
    List<Board> findByWriterStartingWith(String writer);

    // title에 Title 문자열이 포함되거나 content 가 Content 문자열이 포함된 데이터 조회
    List<Board> findByTitleContainingOrContent(String title, String content);

    List<Board> findByTitleContainingOrContentContaining(String title, String content);

    // title에 Title 문자열이 포함되어 있고, id 가 50 보다 큰 게시물 조회
    List<Board> findByTitleContainingAndIdGreaterThan(String title, Long id);

    // id 가 50 보다 큰 게시물 조회 시 내림차순 정렬
    List<Board> findByIdGreaterThanOrderByIdDesc(Long id);

    List<Board> findByIdGreaterThanOrderByIdDesc(Long id, Pageable pageable);
}
