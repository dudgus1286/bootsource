package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.service.BoardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RequestMapping("/board")
@Log4j2
@Controller
public class BoardController {
    private final BoardService service;

    @GetMapping("/create")
    public void getCreate() {
        log.info("Create form 요청");
    }

    @GetMapping("/list")
    public void getList(@ModelAttribute("requestDto") PageRequestDto requestDto, Model model) {
        log.info("List 요청");

        model.addAttribute("result", service.getList(requestDto));
    }

    @GetMapping(value = { "/read", "/modify" })
    public void getRead(@RequestParam Long bno, Model model) {
        log.info("Read of Modify form 요청 {}", bno);
        model.addAttribute("dto", service.getRow(bno));
    }

    @PostMapping("/modify")
    public String postModify(BoardDto dto, RedirectAttributes rttr) {
        log.info("Modify 수정 요청 {}", dto);
        service.modify(dto);

        rttr.addAttribute("bno", dto.getBno());
        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String postRemove(Long bno) {
        service.removeWithReplies(bno);

        return "redirect:/board/list";
    }

}
