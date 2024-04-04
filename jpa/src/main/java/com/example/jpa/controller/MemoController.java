package com.example.jpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jpa.dto.MemoDto;
import com.example.jpa.service.MemoServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/memo")
public class MemoController {

    private final MemoServiceImpl service;

    // /memo/list+Get
    @GetMapping("/list")
    public void listGet(Model model) {
        log.info("list 요청");

        List<MemoDto> list = service.getMemoList();
        // list 를 list.html 에 보여주기
        model.addAttribute("list", list);
    }

    // /memo/read?mno=3 + GET
    // /memo/modify?mno=3 + GET : 수정을 위해 화면 보여주기 요청
    // @GetMapping("/read")
    @GetMapping({ "/read", "/modify" })
    public void read(@RequestParam Long mno, Model model) {
        log.info(mno + "번 메모 조회 요청");

        MemoDto mDto = service.getMemo(mno);
        model.addAttribute("mDto", mDto);

        // 템플릿 찾기
        // /memo/read => templates 폴더 아래 /memo/read.html
        // /memo/modify => templates 폴더 아래 /memo/modify.html
    }

    // /memo/modify + POST : 실제 수정 요청
    @PostMapping("/modify")
    public String modifyPost(MemoDto mDto, RedirectAttributes rttr) {
        log.info(mDto.getMno() + "번 메모 수정 요청");
        MemoDto updateDto = service.updateMemo(mDto);

        // 수정 완료 시 /memo/read 이동
        rttr.addAttribute("mno", mDto.getMno());
        return "redirect:/memo/read";
    }

    // /memo/delete?mno=3 + GET : 삭제요청
    @GetMapping("/delete")
    public String deleteGet(@RequestParam Long mno) {
        log.info(mno + "번 메모 삭제 요청");
        service.deleteMemo(mno);
        // 삭제 성공 시 리스트
        return "redirect:/memo/list";
    }

    // /memo/create + GET : 입력을 위해 화면 보여주기 요청
    @GetMapping("/create")
    // @ModelAttribute 로 이름을 직접 지정하지 않으면 오류남.
    // 아니면 html에서는 ${memoDto.mno}로 표시해야 함
    public void createGet(@ModelAttribute("mDto") MemoDto mDto) {
        log.info("메모 입력 요청");
    }

    // /memo/create + POST : 실제 입력 요청
    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("mDto") MemoDto mDto, BindingResult result,
            RedirectAttributes rttr) {
        log.info("create post 요청 {}", mDto);

        if (result.hasErrors()) {
            return "/memo/create";
        }

        service.createMemo(mDto);
        rttr.addFlashAttribute("result", "SUCCESS");
        return "redirect:/memo/list";
    }

}
