package com.example.book.controller;

import java.util.List;

import org.codehaus.groovy.runtime.callsite.BooleanClosurePredicate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDto;
import com.example.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @GetMapping("/list")
    public void getList(Model model) {
        log.info("list 요청");

        List<BookDto> list = service.getList();
        model.addAttribute("list", list);
    }

    @GetMapping("/create")
    public void getCreate(BookDto dto, Model model) {
        log.info("Create 요청");

        // 테이블명에 있는 카테고리명 보여주기
        model.addAttribute("categories", service.categoryNameList());
    }

    @PostMapping("/create")
    // @Valid 에 Model 의 기능이 있어서 에러가 나도 기존 입력을 유지할 수 있음
    public String postCreate(@Valid BookDto dto, BindingResult result, RedirectAttributes rttr, Model model) {
        log.info("book post 요청 {}", dto);

        if (result.hasErrors()) {
            model.addAttribute("categories", service.categoryNameList());
            return "/book/create";
        }

        Long id = service.bookCreate(dto);
        rttr.addFlashAttribute("result", id);

        return "redirect:/book/list";

    }

    @GetMapping(value = { "/read", "/modify" })
    public void getRead(@RequestParam Long id, Model model, BookDto dto) {
        log.info("read 요청");
        model.addAttribute("dto", service.getRow(id));
    }

    @PostMapping("/modify")
    public String postModify(BookDto dto, RedirectAttributes rttr) {
        log.info("수정 요청 " + dto.getId());

        // 조회화면으로 이동
        Long id = service.priceUpdate(dto);
        rttr.addAttribute("id", id);
        return "redirect:/book/read";
    }

    @PostMapping("/delete")
    public String postDelete(Long id) {
        log.info("도서 삭제 요청 {}", id);
        service.bookDelete(id);
        return "redirect:/book/list";
    }

}
