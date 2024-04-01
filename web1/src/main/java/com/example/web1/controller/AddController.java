package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.AddDto;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequestMapping("/calc")
public class AddController {
    @GetMapping("/add")
    public void addGet() {
        log.info("/calc/add 요청");
    }

    // 사용자 입력값 가져오기
    // 1. HttpServletRequest req
    // 2. 파라메터 이용(폼 이름, 변수명 일치해야 함)
    // 3. DTO 이용 - post 컨트롤러 응답 후 보여지는 화면단에 dto 에 담긴 값 사용가능

    // 1. HttpServletRequest req
    // @PostMapping("/add")
    // public void addPost(HttpServletRequest req) {
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", req.getParameter("num1"));
    // log.info("num2 {}", req.getParameter("num2"));
    // }

    // 2. 파라메터 이용(폼 이름, 변수명 일치)
    // @PostMapping("/add")
    // public void addPost(int num1, int num2) {
    // // input name 만 맞추면 req.getParameter()로 안 가져와도 됨
    // // String 으로 들어온 입력값을 자동으로 지정한 타입int 으로 변환해줌
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", num1);
    // log.info("num2 {}", num2);
    // }

    // @PostMapping("/add")
    // // (@RequestParam("op1") int num1, @RequestParam("op2") int num2)
    // // @RequestPara()은 기본값 defaultValue 지정 가능
    // public void addPost(@RequestParam(value = "op1", defaultValue = "0") int
    // num1,
    // @RequestParam(value = "op2", defaultValue = "0") int num2) {
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", num1);
    // log.info("num2 {}", num2);
    // }

    // 3. DTO 이용
    @PostMapping("/add")
    public void addPost(AddDto dto, Model model) {
        log.info("/calc/add post 요청");
        log.info("num1 {}", dto.getNum1());
        log.info("num2 {}", dto.getNum2());

        // dto.setResult(dto.getNum1() + dto.getNum2());
        model.addAttribute("result", dto.getNum1() + dto.getNum2());
    }
}
