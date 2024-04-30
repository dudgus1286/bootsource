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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

}
