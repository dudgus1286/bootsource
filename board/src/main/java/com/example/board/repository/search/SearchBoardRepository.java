package com.example.board.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;

public interface SearchBoardRepository {
    // Object[] : 전체 조회 시 Board, Member, Reply 정보를 다 조회해야 함
    Page<Object[]> list(String type, String keyword, Pageable pagealbe);

    // 상세 조회
    Object[] getRow(Long bno);
}
