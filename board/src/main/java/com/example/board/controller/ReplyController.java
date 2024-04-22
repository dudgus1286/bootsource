package com.example.board.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.ReplyDto;
import com.example.board.service.ReplyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService service;

    // http://localhost:8080/replies/board/100
    @GetMapping("/board/{bno}")
    public List<ReplyDto> getListByBoard(@PathVariable("bno") Long bno) {
        log.info("댓글 가져오기 {}", bno);
        List<ReplyDto> replies = service.getReplies(bno);

        return replies;
    }

    // /replies/new
    @PostMapping("/new")
    public ResponseEntity<Long> postMethodName(@RequestBody ReplyDto dto) {
        log.info("댓글 등록 {}", dto);

        return new ResponseEntity<Long>(service.create(dto), HttpStatus.OK);
    }

    // /replies/1 + DELETE 방식
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> delete(@PathVariable("rno") Long rno) {
        log.info("댓글 제거 {}", rno);
        service.remove(rno);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // /replies/1 + GET 방식 <- 데이터를 보내는 방식에 차이를 줘서 같은 주소를 다른 용도로 씀
    @GetMapping("/{rno}")
    public ResponseEntity<ReplyDto> getRow(@PathVariable("rno") Long rno) {
        log.info("댓글 하나 요청 {}", rno);
        return new ResponseEntity<ReplyDto>(service.getReply(rno), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> putMethodName(@PathVariable("id") String id, @RequestBody ReplyDto replyDto) {
        log.info("댓글 수정 요청 {} {}", id, replyDto);
        Long rno = service.update(replyDto);
        return new ResponseEntity<String>(String.valueOf(rno), HttpStatus.OK);
    }
}
