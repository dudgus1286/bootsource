package com.example.guestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.service.GuestBookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/guestbook")
@Log4j2
@Controller
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService service;

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("list 요청");

        PageResultDto<GuestBookDto, GuestBook> result = service.getList(requestDto);
        model.addAttribute("result", result);
    }

    @GetMapping(value = { "/read", "/modify" })
    public void getRead(@RequestParam Long gno, @ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("read or modify 요청");
        model.addAttribute("guestbookDto", service.getRow(gno));
    }

    @PostMapping("/modify")
    public String postModify(GuestBookDto guestbookDto, @ModelAttribute("requestDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {
        // String 형태의 날짜를 dto의 로컬데이터타임 타입 변수에 담을 수 없어서 오류 남
        // html 의 폼 태그에서 해당 인풋 영역의 네임 제거
        log.info("modify post 요청 {}", guestbookDto);
        service.modify(guestbookDto);

        rttr.addAttribute("gno", guestbookDto.getGno());
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/guestbook/list";
    }

    @PostMapping("/remove")
    public String postRemove(@RequestParam Long gno, @ModelAttribute("requestDto") PageRequestDto requestDto,
            RedirectAttributes rttr) {
        log.info("delete post 요청 {}", gno);
        service.remove(gno);

        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());

        return "redirect:/guestbook/list";
    }

    @GetMapping("/create")
    public void getCreate(GuestBookDto dto) {
        log.info("등록 폼 요청");
    }

    // 메소드에 담는 변수: @Valid 유효성검증 기준 객체 변수 바로 뒤에 BindingResult 변수 선언해야 함
    @PostMapping("/create")
    public String postCreate(@Valid GuestBookDto dto, BindingResult result,
            @ModelAttribute("requestDto") PageRequestDto requestDto, RedirectAttributes rttr) {
        log.info("등록 요청 {}", dto);
        if (result.hasErrors()) {
            return "/guestbook/create";
        }
        Long gno = service.create(dto);
        rttr.addFlashAttribute("msg", gno);
        rttr.addAttribute("page", requestDto.getPage());
        rttr.addAttribute("type", requestDto.getType());
        rttr.addAttribute("keyword", requestDto.getKeyword());
        return "redirect:/guestbook/list";
    }

}
