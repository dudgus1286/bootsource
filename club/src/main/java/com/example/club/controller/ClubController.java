package com.example.club.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
@RequestMapping("/club")
public class ClubController {
    @GetMapping("/member")
    public void getMember() {
        log.info("member page 요청");
    }

    @GetMapping("/manager")
    public void getManager() {
        log.info("manager page 요청");
    }

    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin page 요청");
    }

}
