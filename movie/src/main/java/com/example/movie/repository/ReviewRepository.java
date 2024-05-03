package com.example.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // DELETE FROM MOVIE_IMAGE mi WHERE mi.movie_mno = 1; ==> 메소드 생성 필요
    // delete(), deleteById() => Review 의 id 가 기준
    @Modifying
    @Query("delete from Review r where r.movie = :movie")
    void deleteByMovie(Movie movie);

    // movie 번호를 이용해 리뷰 가져오기
    // 이 메소드 실행 시 join 구문으로 처리하도록 지시
    @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    // @Query(value = "delete from review r where r.member = :member",
    // nativQuery = true)
    @Modifying
    @Query("delete from Review r where r.member = :member")
    void deleteByMember(Member member); // @Query 없으면 review_no를 기준으로 동작
    // (리뷰 작성이 많은 사람일수록 delete 구문이 여러 번 실행됨, 비효율적임)
}
