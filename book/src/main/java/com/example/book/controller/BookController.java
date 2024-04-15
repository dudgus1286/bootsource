package com.example.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @GetMapping("/list")
    public void getList(Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("list 요청");

        PageResultDto<BookDto, Book> result = service.getList(requestDto);
        model.addAttribute("result", result);
    }

    @GetMapping("/create")
    public void getCreate(BookDto dto, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("Create 요청");

        // 테이블명에 있는 카테고리명 보여주기
        model.addAttribute("categories", service.categoryNameList());
    }

    @PostMapping("/create")
    // @Valid 에 Model 의 기능이 있어서 에러가 나도 기존 입력을 유지할 수 있음
    public String postCreate(@Valid BookDto dto, BindingResult result, RedirectAttributes rttr, Model model,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("book post 요청 {}", dto);

        if (result.hasErrors()) {
            model.addAttribute("categories", service.categoryNameList());
            return "/book/create";
        }

        // insert 작성
        Long id = service.bookCreate(dto);

        // 페이지 나누기 정보
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        rttr.addFlashAttribute("msg", id);

        return "redirect:/book/list";

    }

    @GetMapping(value = { "/read", "/modify" })
    public void getRead(Long id, Model model, @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("read 요청");
        model.addAttribute("dto", service.getRow(id));
    }

    @PostMapping("/modify")
    public String postModify(BookDto dto, RedirectAttributes rttr,
            @ModelAttribute("requestDto") PageRequestDto requestDto) {
        log.info("수정 요청 " + dto.getId());
        log.info("페이지 나누기 정보 " + requestDto);

        // 조회화면으로 이동
        Long id = service.priceUpdate(dto);
        rttr.addAttribute("id", id);
        // 페이지 나누기 정보
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/book/read";
    }

    @PostMapping("/delete")
    // @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String postDelete(Long id, @ModelAttribute("requestDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {
        log.info("도서 삭제 요청 {}", id);
        service.bookDelete(id);

        // 페이지 나누기 정보
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/book/list";
    }

}
