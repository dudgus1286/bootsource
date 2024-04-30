package com.example.movie.repository.total;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieImageReviewRepository {
    // 전체 리스트
    Page<Object[]> getTotalList(String type, String keyword, Pageable pageable);

    // 특정 영화 조회
    List<Object[]> getMovieRow(Long mno);
}
