package com.example.rest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.rest.dto.SampleDto;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 컨트롤러
// @Controller - 메소드가 끝나고 찾는 대상은 템플릿임
// @RestController - 데이터 자체를 리턴 가능함
// 객체 ==> json 로 변환하는 컨버터가 필요

@Log4j2
@RestController
public class RestControllerTest {
    @GetMapping("/hello")
    public String getHello() {
        log.info("hello 요청");
        return "Hello World";
    }

    // JSON : JavaScript Object Notation : JavaScript 객체 문법
    // 서버와 클라이언트 사이에 데이터 교환 시 사용함
    // 자바스크립트 객체 표현 방식: {key:value,key:value}
    // json : {"key":value,"key":value}
    @GetMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE)
    public SampleDto getSample() {
        SampleDto dto = new SampleDto();
        dto.setMno(1L);
        dto.setFirstName("홍");
        dto.setLastName("길동");
        return dto;
    }

    @GetMapping(value = "/sample2", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SampleDto> getSample2() {
        List<SampleDto> list = new ArrayList<>();
        LongStream.rangeClosed(1, 10).forEach(i -> {

            SampleDto dto = new SampleDto();
            dto.setMno(i);
            dto.setFirstName("홍");
            dto.setLastName("길동");
            list.add(dto);
        });
        return list;
    }

    // 데이터 + 상태코드(Http 상태 코드 - 200, 500, 404 등)
    // ResponseEntity(객체) 사용
    // check?height=150&weight=45
    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SampleDto> getCheck(double height, double weight) {
        SampleDto dto = new SampleDto();
        dto.setMno(1L);
        dto.setFirstName(String.valueOf(height));
        dto.setLastName(String.valueOf(weight));

        if (height < 150) {
            return new ResponseEntity<SampleDto>(dto, HttpStatus.BAD_REQUEST);
        } else {
            // 200 : OK
            return new ResponseEntity<SampleDto>(dto, HttpStatus.OK);
        }
    }

    // @PathVariable : @RequestMapping, @GetMapping 에 담아둔 경로에 있는 값을 지정
    // /product/bags/1234 (== /product?category=bags&pid=1234)
    @GetMapping("/product/{cat}/{pid}")
    public String[] getMethodName(@PathVariable("cat") String cat, @PathVariable("pid") String pid) {
        return new String[] {
                "category: " + cat,
                "productId: " + pid
        };
    }

}
