package com.example.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.todo.dto.TodoDto;
import com.example.todo.service.TodoService;
import com.example.todo.service.TodoServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/todo")
public class TodoController {
    private final TodoService service;

    // / 로 접속 시 list.html 로 연결
    @GetMapping(value = "/list")
    public String list(Model model) {
        log.info("list 요청");

        List<TodoDto> list = service.getList();
        model.addAttribute("list", list);

        return "/todo/list";
    }

    @GetMapping("/create")
    public void getCreate() {
        log.info("create 요청");
    }

    @PostMapping("/create")
    public String postCreate(TodoDto dto, RedirectAttributes rttr) {
        TodoDto result = service.create(dto);

        rttr.addFlashAttribute("msg", result.getId());
        return "redirect:/todo/list";
    }

    @GetMapping("/read")
    public void getRead(@RequestParam Long id, Model model) {
        log.info("read 요청 {}", id);
        model.addAttribute("dto", service.getTodo(id));

    }

    @GetMapping("/done")
    public void getDone(Model model) {
        log.info("done 요청");
        model.addAttribute("list", service.getCompletedList());

    }

    @PostMapping("/update")
    public String todoUpdate(@RequestParam Long id, RedirectAttributes rttr) {
        // id 값 받기
        // completed = true 로 변경
        Long id2 = service.todoUpdate(id);

        rttr.addAttribute("id", id2);
        // /read로 이동
        return "redirect:/todo/read";
    }

    @PostMapping("/delete")
    public String todoDelete(@RequestParam Long id) {
        service.todoDelete(id);

        return "redirect:/todo/list";
    }

}
