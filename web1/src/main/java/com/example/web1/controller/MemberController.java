package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.MemberDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/login")
    public void login() {
        log.info("로그인 페이지 요청");
    }

    // 데이터 가져오기
    // @PostMapping("/login")
    // public void loginPost(String email, String name) {
    // log.info("로그인 정보 가져오기");
    // log.info("email {}", email);
    // log.info("name {}", name);
    // }

    @PostMapping("/login")
    // public String loginPost(MemberDto mDto, int page, Model model) {
    // 값이름을 mDto로 설정
    // public String loginPost(@ModelAttribute("mDto") MemberDto mDto, int page,
    // Model model)
    public String loginPost(@ModelAttribute("mDto") MemberDto mDto, @ModelAttribute("page") int page, Model model) {
        log.info("로그인 정보 가져오기");
        log.info("email {}", mDto.getEmail());
        log.info("name {}", mDto.getName());
        log.info("page {}", page);

        // 데이터 보내기 : Model - forward 방식
        // Servlet, JSP의 request.setAttribute("이름", 값)와 같은 방식
        // model.addAttribute("page", page);
        return "/member/info";
    }

}
