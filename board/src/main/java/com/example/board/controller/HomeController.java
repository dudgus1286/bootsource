package com.example.board.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.PageRequestDto;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
public class HomeController {
    // role: member, admin
    // /, /board/list, /board/read => 전체 접근 가능

    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public String getHome(RedirectAttributes rttr, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("Home 요청");

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/board/list";
    }

    @PreAuthorize("permitAll()")
    @ResponseBody
    @GetMapping("/auth")
    public Authentication getMethodName() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    }

}
