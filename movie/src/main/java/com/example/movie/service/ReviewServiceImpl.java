package com.example.movie.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.movie.dto.ReviewDto;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import com.example.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDto> getListOfMovie(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> reviews = reviewRepository.findByMovie(movie);

        // List<Review> => List<ReviewDto>
        Function<Review, ReviewDto> fn = review -> entityToDto(review);
        return reviews.stream().map(fn).collect(Collectors.toList());
    }

    @Override
    public Long addReview(ReviewDto reviewDto) {
        return reviewRepository.save(dtoToEntity(reviewDto)).getReviewNo();
    }

    @Override
    public void removeReview(Long reviewNo) {
        reviewRepository.deleteById(reviewNo);
    }

    @Override
    public ReviewDto getReview(Long reviewNo) {
        return entityToDto(reviewRepository.findById(reviewNo).get());
    }

    @Override
    public Long updateReview(ReviewDto reviewDto) {
        // save() =>
        // 1 ) select 구문 실행 , 2) insert or update 결정
        // return reviewRepository.save(dtoToEntity(reviewDto)).getReviewNo();

        Optional<Review> result = reviewRepository.findById(reviewDto.getReviewNo());

        if (result.isPresent()) {
            Review review = result.get();
            review.setText(reviewDto.getText());
            review.setGrade(reviewDto.getGrade());
            return reviewRepository.save(review).getReviewNo();
        }
        return reviewDto.getReviewNo();
    }

}
