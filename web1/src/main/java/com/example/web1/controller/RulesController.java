package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.AddDto;
import com.example.web1.dto.RulesDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequestMapping("/calc")
public class RulesController {
    @GetMapping("/rules")
    public void rulesGet() {
        log.info("/calc/rules 요청");
    }

    @PostMapping("/rules")
    public String result(AddDto rDto, @ModelAttribute("op") String op, Model model) {
        log.info("/calc/result 요청");
        log.info("num1 {}", rDto.getNum1());
        log.info("calc {}", op);
        log.info("num1 {}", rDto.getNum2());

        int result = 0;
        switch (op) {
            case "+":
                result = rDto.getNum1() + rDto.getNum2();
                break;
            case "-":
                result = rDto.getNum1() - rDto.getNum2();
                break;
            case "*":
                result = rDto.getNum1() * rDto.getNum2();
                break;
            case "/":
                if (rDto.getNum1() != 0 && rDto.getNum2() != 0) {
                    result = rDto.getNum1() / rDto.getNum2();
                }
                break;
            default:
                break;
        }

        // model.addAttribute("result", result);
        rDto.setResult(result);

        log.info("result {}", result);
        return "calc/result";
    }

}
