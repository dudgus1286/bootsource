package com.example.movie.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.movie.dto.PageRequestDto;

@Log4j2
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        log.info("home 요청");
        return "redirect:/movie/list";
    }

    @GetMapping("/access-denied")
    public void get403(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("접근 제한");
    }

    @GetMapping("/error")
    public String get404(@ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("404");
        return "/except/url404";
    }

    @PreAuthorize("permitAll()")
    @ResponseBody
    @GetMapping("/auth")
    public Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    }
}
