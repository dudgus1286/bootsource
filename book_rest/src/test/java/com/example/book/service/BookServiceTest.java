package com.example.book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService service;

    @Transactional
    @Test
    public void testList() {
        PageRequestDto requestDto = PageRequestDto.builder().page(1).size(10).build();
        PageResultDto<BookDto, Book> pageResultDto = service.getList(requestDto);

        System.out.println("------------------- 페이지 나누기 정보 -------------------");
        System.out.println("prev " + pageResultDto.isPrev());
        System.out.println("next " + pageResultDto.isNext());
        System.out.println("total " + pageResultDto.getTotalPage());
        System.out.println("pageList " + pageResultDto.getPageList());

        // pageResultDto.getDtoList().forEach(System.out::println);
    }

    @Transactional
    @Test
    public void testSearchList() {
        PageRequestDto requestDto = PageRequestDto.builder().page(1).size(10).type("t").keyword("제목").build();
        PageResultDto<BookDto, Book> pageResultDto = service.getList(requestDto);

        pageResultDto.getDtoList().forEach(System.out::println);
    }
}
