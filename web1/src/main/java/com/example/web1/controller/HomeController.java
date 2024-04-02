package com.example.web1.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    // response.sendRedirect("/qList.do");
    @GetMapping("/ex3")
    // RedirectAttributes : redirect 시 데이터 전달
    public String ex3(RedirectAttributes rttr) {
        log.info("/ex3 요청");
        // rttr.addAttribute("이름",값); 값이 파라메터로 전달됨 == path+="?bno="+bno;
        // thymeleaf 에서 불려들일 때는 ${param.이름}
        // rttr.addAttribute("bno", 15); // http://localhost:8080/sample/basic?bno=15
        // thymeleaf 에서 불려들일 때는 ${이름} - Model 과 동일

        // rttr.addFlashAttribute("이름",값); 값을 세션에 임시로 담음
        rttr.addFlashAttribute("bno", 15);

        // return "redirect:/"; // index 페이지로 이동
        return "redirect:/sample/basic"; // 경로 지정, 다른 컨트롤러에 있는 경로도 가능
    }
    // forward 방식의 연결(return "/")은 값 넘길 때 Model을 사용함

    // IllegalStateException: Ambiguous mapping
    // => 전체 컨트롤러에서 동일한 매핑방식과 경로 지정이 문제

    // GetMapping() 경로 중복되서 생긴 에러
    // @GetMapping("/ex3")
    // public void ex4() {
    // log.info("/ex4 요청");
    // }
}
