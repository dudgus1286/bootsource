package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.dto.MemberDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.service.MemberService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;

    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public void getLogin(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("로그인 폼 요청");
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/register")
    public void getRegister(@ModelAttribute("requestDto") PageRequestDto requestDto, MemberDto memberDto) {
        log.info("회원가입 폼 요청");
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("requestDto") PageRequestDto requestDto, @Valid MemberDto memberDto,
            BindingResult result) {
        log.info("회원가입 요청 {}", memberDto);
        if (result.hasErrors()) {
            return "/member/register";
        }
        service.register(memberDto);
        return "redirect:/member/login";
    }

}
