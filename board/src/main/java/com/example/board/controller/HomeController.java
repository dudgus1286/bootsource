package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {
    @GetMapping("/")
    public String getHome() {
        log.info("Home 요청");
        return "redirect:/board/list";
    }

}
