package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@RequestMapping("/board")
@Log4j2
@Controller
public class BoardController {
    @GetMapping("/create")
    public void create() {
        log.info("/board/create 요청");
    }
}
