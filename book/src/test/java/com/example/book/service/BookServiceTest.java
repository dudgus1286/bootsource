package com.example.book.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.dto.BookDto;
import com.example.book.repository.BookRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService service;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void listTest() {
        service.getList().forEach(book -> System.out.println(book));
    }

    @Transactional
    @Test
    public void createTest() {
        // System.out.println(service.bookCreate(BookDto.builder().title("1").writer("2").categoryName("컴퓨터")
        // .publisherName("로드북").price(11).salePrice(12).build()));

        System.out.println(bookRepository.findById(12L).get());

    }
}
