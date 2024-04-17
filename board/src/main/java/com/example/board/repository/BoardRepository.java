package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.board.entity.Board;
import com.example.board.repository.search.SearchBoardRepository;

public interface BoardRepository
        extends JpaRepository<Board, Long>, SearchBoardRepository, QuerydslPredicateExecutor<Board> {

}
