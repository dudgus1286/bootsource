package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {
    // / 로 접속 시 list.html 로 연결
    @GetMapping(value = "/")
    public String list(Model model) {
        log.info("home 요청");

        return "redirect:/todo/list";
    }
}
