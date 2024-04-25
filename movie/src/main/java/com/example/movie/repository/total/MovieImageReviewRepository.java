package com.example.movie.repository.total;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieImageReviewRepository {
    Page<Object[]> getTotalList(Pageable pageable);
}
