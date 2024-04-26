package com.example.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // DELETE FROM MOVIE_IMAGE mi WHERE mi.movie_mno = 1; ==> 메소드 생성 필요
    // delete(), deleteById() => Review 의 id 가 기준
    @Modifying
    @Query("delete from Review r where r.movie = :movie")
    void deleteByMovie(Movie movie);
}
