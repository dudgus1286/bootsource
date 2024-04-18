package com.example.book.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class BookRestController {
    private final BookService service;

    // /page/1 => 10 개 데이터 가져오기

    @GetMapping("/pages/{page}")
    public ResponseEntity<PageResultDto<BookDto, Book>> getList(@PathVariable("page") int page) {
        PageRequestDto requestDto = new PageRequestDto();
        requestDto.setPage(page);
        PageResultDto<BookDto, Book> result = service.getList(requestDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // /read/15
    @GetMapping("/read/{id}")
    public ResponseEntity<BookDto> getRead(@PathVariable("id") Long id) {
        BookDto bookDto = service.getRow(id);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @PostMapping("/book/new")
    // @RequestBody : json 으로 넘어오는 데이터를 객체에 바인딩해줌
    public ResponseEntity<String> postCreate(@RequestBody @Valid BookDto dto) {

        // insert 작성
        Long id = service.bookCreate(dto);
        // Valid 유효성 검증 통가환 경우
        return new ResponseEntity<String>("success", HttpStatus.OK);

    }

    // Valid 유효성 검증 실패한 경우
    // Map<String, String> - "key", value 의 형태로 담기 위해서
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
    }

    // /modify/1
    @PutMapping("/modify/{id}")
    public ResponseEntity<String> postModify(@RequestBody BookDto updateDto, @PathVariable("id") Long id) {

        service.priceUpdate(updateDto);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    // @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> postDelete(@PathVariable("id") Long id) {
        service.bookDelete(id);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}
