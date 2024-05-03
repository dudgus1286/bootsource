package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movie.dto.AuthMemberDto;
import com.example.movie.dto.MemberDto;
import com.example.movie.dto.PageRequestDto;
import com.example.movie.dto.PasswordChangeDto;
import com.example.movie.service.MovieUserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/member")
@Log4j2
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MovieUserService service;

    @GetMapping("/login")
    public void getLogin(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("로그인 폼 요청");
    }

    // /profile => profile.html 보여주기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public void getProfile(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("프로필 페이지 요청 {}");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public String getEditForm(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("프로필 수정폼 요청 {}");
        return "/member/edit-profile";
    }

    // /edit/nickname
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/nickname")
    public String postNickname(MemberDto memberDto, HttpSession session) {
        log.info("닉네임 수정 요청 {}", memberDto);
        service.nickNameUpdate(memberDto);
        // SecurityContent 안에 저장된 Authentication 변경되지 않음
        // 1) 현재 세션 제거 후 재로그인
        // session.invalidate();

        // 2) Authentication 업데이트
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        AuthMemberDto authMemberDto = (AuthMemberDto) authentication.getPrincipal();
        authMemberDto.getMemberDto().setNickname(memberDto.getNickname());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/member/profile";
    }

    // /edit/password
    @PostMapping("/edit/password")
    public String postPassword(PasswordChangeDto pDto, HttpSession session, RedirectAttributes rttr) {
        // 현재 비번이 틀리면 /member/edit
        log.info("비밀번호 변경 요청 {}", pDto);
        try {
            service.passwordUpdate(pDto);
        } catch (Exception e) {
            rttr.addFlashAttribute("error", e.getMessage());
            return "redirect:/member/edit";
        }
        session.invalidate();
        return "redirect:/member/login";
    }

    @GetMapping("/register")
    public void getRegister(@ModelAttribute("requestDto") PageRequestDto pageRequestDto, MemberDto memberDto) {
        log.info("회원가입 폼 요청");
    }

    @PostMapping("/register")
    public String postRegister(@Valid MemberDto insertDto, BindingResult result, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("회원가입 요청 {}", insertDto);

        if (result.hasErrors()) {
            return "/member/register";
        }

        String email = "";
        try {
            email = service.register(insertDto);
        } catch (Exception e) {
            rttr.addFlashAttribute("error", e.getMessage());
            rttr.addFlashAttribute("memberDto", insertDto);
            return "redirect:/member/register";
        }
        rttr.addFlashAttribute("email", email);
        return "redirect:/member/login";
    }

    @GetMapping("/leave")
    public void getLeaveForm(@ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("회원탈퇴");
    }

    @PostMapping("/leave")
    public String postLeave(MemberDto memberDto, RedirectAttributes rttr, HttpSession session,
            @ModelAttribute("requestDto") PageRequestDto pageRequestDto) {
        log.info("회원탈퇴 요청 {}", memberDto);
        try {
            service.leave(memberDto);
        } catch (Exception e) {
            rttr.addFlashAttribute("error", "이메일이나 비밀번호를 확인해 주세요");
            return "redirect:/member/leave";
        }

        session.invalidate();
        return "redirect:/";
    }

}
