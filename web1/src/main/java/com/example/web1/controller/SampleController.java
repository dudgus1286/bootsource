package com.example.web1.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web1.dto.SampleDto;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * 어노테이션
 * @Component : 스프링 컨테이너의 관리가 필요한 모든 객체에 사용
 * (Component 이름에 기능을 표시한 게 Controller와 Service)
 * @Controller : 컨트롤러 클래스 위에 설정 / 스프링 컨테이너가 해당 클래스의 객체를 생성 후 관리함
 * @Service : 서비스 클래스 위에 설정 / 스프링 컨테이너가 해당 클래스의 객체를 생성한 후 관리해줌
 * 
 * @Autowired : 이미 생성된 객체를 주입해줌
 * private MemoRepository memoRepository = new MemoRepository() (스프링부트에서는 더이상 안 함)
 * MemoRepository 객체는 스프링 컨테이너가 생성해서 이미 가지고 있기 때문에
 * 
 * @RequestMapping(value="", method=) : 경로 지정(옛날버전)
 * @GetMapping("경로") : @RequestMapping 의 GET 간략화 버전
 * @PostMapping("경로") : @RequestMapping 의 POST 간략화 버전
 * 
 * @RequestParam("변수명")/@RequestParam(value="변수명", defaultValue="없을경우의 기본값")
 *  : request에서 넘어오는 파라메터의 이름을 지정할 때 사용
 * @ModelAttribute("객체한테 붙어줄 이름") : 객체 이름 지정,
 *  : model.addAttribute("이름", 값) 과 동일
 * RedirectAttributes rttr : 경로에 값 추가 rttr.addAttribute("이름", 값)
 */

// 모든 요청은 컨트롤러에 경로가 있어야 함
// 직접적으로 파일 링크 지정 불가: <a href="index.html">index</a> (X)
// 컨트롤러로 경로 지정: <a href="/">index</a> (O)

@Log4j2
@Controller
@RequestMapping("/sample")
public class SampleController {
    // String, void 형태의 메소드 작성
    // 404 : 경로 없음(컨트롤러 매핑 주소 확인)
    // 500 : 다양함
    // Error resolving template [sample/basic], template might not exist or
    // might not be accessible by any of the configured Template Resolvers
    // (templates 폴더 확인)
    // Internal Server Error, status=500
    // (변수명 틀림)
    // 400 : Bad Request, status=400
    // Failed to convert value of type 'java.lang.String' to required type 'int'

    // 리턴타입 결정
    // void : 경로와 일치하는 곳에 템플릿이 존재할 때
    // String : 경로와 일치하는 곳에 템플릿이 없을 때(템플릿의 위치와 관계없이 자유롭게 지정 가능)

    // http://localhost:8080/sample/basic 요청

    // 경로 마지막 부분(/basic 부분)을 무조건 .html 파일 이름으로 일치시킴. 그 앞 경로는 폴더명
    // public class 상단에 @RequestMapping("/sample") 추가하면 중복되는 경로 축약 가능
    // @GetMapping("/sample/basic")
    @GetMapping("/basic")
    public void basic(Model model) {
        log.info("/sample/basic 요청");
        model.addAttribute("name", "홍길동");

        // 기본 방식
        // SampleDto sampleDto = new SampleDto();
        // sampleDto.setFirst("first");
        // sampleDto.setId(1l);
        // sampleDto.setLast("last");
        // sampleDto.setRegTime(LocalDateTime.now());

        // lombok Builder 패턴 이용
        SampleDto sampleDto = SampleDto.builder()
                .first("first").id(1L).last("last").regTime(LocalDateTime.now())
                .build();
        model.addAttribute("dto", sampleDto);

        List<SampleDto> list = new ArrayList<>();
        for (Long i = 1L; i < 21; i++) {
            SampleDto dto = SampleDto.builder()
                    .first("first" + i).id(i).last("last" + i).regTime(LocalDateTime.now())
                    .build();
            list.add(dto);
        }
        model.addAttribute("list", list);

        model.addAttribute("now", new Date());
        model.addAttribute("price", 123456789);
        model.addAttribute("title", "This is a just sample");
        model.addAttribute("options", Arrays.asList("AAAA", "BBBB", "CCCC", "DDDD"));
    }

    // http://localhost:8080/sample/ex1 요청
    // @GetMapping("/sample/ex1")
    @GetMapping("/ex1")
    public void ex1(Model model) {
        log.info("/sample/ex1 요청");
        model.addAttribute("result", "SUCCESS");
    }

    @GetMapping("/ex2")
    public String ex2() {
        log.info("/sample/ex2 요청");
        // return ""; 큰따옴표 안에 templates 폴더 밑 경로 지정
        // return "/board/create";
        return "/index";
    }

    @GetMapping("/ex3")
    public void ex3() {
        log.info("/sample/ex3 요청");
    }

    @GetMapping("/ex4")
    public void ex4(String param1, String param2, Model model) {
        log.info("/sample/ex4 요청");
        log.info("param1 {}, param2 {}", param1, param2);
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);

    }

    @GetMapping("/ex5")
    public void ex5() {
        log.info("/sample/ex5 요청");
    }

    @GetMapping("/ex6")
    public void ex6() {
        log.info("/sample/ex6 요청");
    }

}
