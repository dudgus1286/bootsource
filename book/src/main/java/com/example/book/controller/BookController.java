package com.example.book.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/book")
public class BookController {
    @GetMapping("/list")
    public void getList() {
        log.info("Home 요청");
    }

    @GetMapping("/create")
    public void getCreate() {
        log.info("Create 요청");
    }
}
