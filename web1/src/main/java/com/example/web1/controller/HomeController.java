package com.example.web1.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class HomeController {

    // 값 경로, 접속 방식(get, post)
    // @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/")
    public String home() {
        // c.e.web1.controller.HomeController : home 요청
        log.info("home 요청"); // sout 과 같은 기능

        // templates 아래 경로부터 시작, 확장자 빼고 파일명만
        return "index";
    }
}
