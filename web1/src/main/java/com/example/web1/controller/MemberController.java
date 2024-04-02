package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.LoginDto;
import com.example.web1.dto.MemberDto;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/login")
    // void login() 괄호 안의 LoginDto loginDto == new LoginDto();
    public void login(LoginDto loginDto) {
        log.info("로그인 페이지 요청");
    }

    // 데이터 가져오기
    // @PostMapping("/login")
    // public void loginPost(String email, String name) {
    // log.info("로그인 정보 가져오기");
    // log.info("email {}", email);
    // log.info("name {}", name);
    // }

    // @PostMapping("/login")
    // // public String loginPost(MemberDto mDto, int page, Model model) {
    // // 값이름을 mDto로 설정
    // // public String loginPost(@ModelAttribute("mDto") MemberDto mDto, int page,
    // // Model model)
    // public String loginPost(@ModelAttribute("mDto") LoginDto mDto,
    // @ModelAttribute("page") int page, Model model) {
    // log.info("로그인 정보 가져오기");
    // log.info("email {}", mDto.getEmail());
    // log.info("name {}", mDto.getName());
    // log.info("page {}", page);

    // // 데이터 보내기 : Model - forward 방식
    // // Servlet, JSP의 request.setAttribute("이름", 값)와 같은 방식
    // // model.addAttribute("page", page);
    // return "/member/info";
    // }

    @PostMapping("/login")
    // @Valid LoginDto : LoginDto 의 유효성 검사 실행
    // BindingResult : 유효성 검사 후 오류가 있을 경우 검사 결과가 담기는 개체
    public String loginPost(@Valid LoginDto mDto, BindingResult result) {
        log.info("로그인 정보 가져오기");
        log.info("email {}", mDto.getEmail());
        log.info("name {}", mDto.getName());

        // 유효성 검증을 통과하지 못한다면
        if (result.hasErrors()) {
            return "/member/login";
        }

        return "/member/info";
    }

    // /member/join + GET
    @GetMapping("/join")
    public void joinGet(MemberDto memberdto) {
        log.info("/member/join 요청");
    }

    // /member/join + POST
    @PostMapping("/join")
    public String joinPost(@Valid MemberDto memberDto, BindingResult result) {
        // 유효성 검증 통과 안 되면
        if (result.hasErrors()) {
            return "/member/join";
        }
        return "redirect:/member/login";
    }

}
