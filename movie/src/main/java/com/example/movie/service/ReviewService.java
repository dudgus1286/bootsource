package com.example.movie.service;

import java.util.List;

import com.example.movie.dto.ReviewDto;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;

public interface ReviewService {
    // 특정 영화의 모든 리뷰 가져오기
    List<ReviewDto> getListOfMovie(Long mno);

    // 특정 영화의 리뷰 등록
    Long addReview(ReviewDto reviewDto);

    void removeReview(Long reviewNo);

    ReviewDto getReview(Long reviewNo);

    Long updateReview(ReviewDto reviewDto);

    public default ReviewDto entityToDto(Review entity) {
        Member member = entity.getMember();
        Movie movie = entity.getMovie();

        ReviewDto dto = ReviewDto.builder()
                .reviewNo(entity.getReviewNo())
                .grade(entity.getGrade())
                .text(entity.getText())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .mid(member.getMid())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .mno(movie.getMno())
                .build();
        return dto;
    }

    public default Review dtoToEntity(ReviewDto dto) {
        Member member = Member.builder().mid(dto.getMid()).build();
        Movie movie = Movie.builder().mno(dto.getMno()).build();

        Review entity = Review.builder()
                .reviewNo(dto.getReviewNo())
                .grade(dto.getGrade())
                .text(dto.getText())
                .member(member)
                .movie(movie)
                .build();
        return entity;
    }
}
