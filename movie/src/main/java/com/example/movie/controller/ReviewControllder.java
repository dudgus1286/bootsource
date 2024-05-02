package com.example.movie.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.movie.dto.ReviewDto;
import com.example.movie.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Log4j2
public class ReviewControllder {

    private final ReviewService service;

    // /3/all
    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDto>> getReviewList(@PathVariable("mno") Long mno) {
        log.info("영화 리뷰 요청 {}", mno);

        List<ReviewDto> reviews = service.getListOfMovie(mno);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // /3 + POST = 리뷰번호 리턴
    @PostMapping("/{mno}")
    public ResponseEntity<Long> postReview(@PathVariable("mno") Long mno, @RequestBody ReviewDto reviewDto) {
        log.info("리뷰 등록 {}", reviewDto);
        Long reviewNo = service.addReview(reviewDto);

        return new ResponseEntity<>(reviewNo, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewNo}")
    public ResponseEntity<Long> deleteReview(@PathVariable("mno") Long mno, @PathVariable("reviewNo") Long reviewNo) {
        log.info("리뷰 삭제 요청 {}", reviewNo);
        service.removeReview(reviewNo);
        return new ResponseEntity<>(reviewNo, HttpStatus.OK);
    }

    // /299/5 + GET
    @GetMapping("/{mno}/{reviewNo}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable("mno") Long mno, @PathVariable("reviewNo") Long reviewNo) {
        log.info("개별 리뷰 요청 {}", reviewNo);
        ReviewDto dto = service.getReview(reviewNo);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewNo}")
    public ResponseEntity<Long> putReview(@PathVariable("mno") Long mno, @PathVariable("reviewNo") Long reviewNo,
            @RequestBody ReviewDto reviewDto) {
        log.info("리뷰 수정 요청 {}", reviewDto);
        service.updateReview(reviewDto);

        return new ResponseEntity<Long>(reviewNo, HttpStatus.OK);
    }

}
