package com.example.guestbook.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.guestbook.entity.GuestBook;

@SpringBootTest
public class GuestBookRepositoryTest {
    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder().title("타이틀 " + i).writer("작성자 " + (i % 10)).content("내용 " + i)
                    .build();
            guestBookRepository.save(guestBook);
        });
    }

    @Test
    public void testList() {
        guestBookRepository.findAll().forEach(guest -> System.out.println(guest));
    }

    @Test
    public void testRow() {
        System.out.println(guestBookRepository.findById(227L).get());
    }

    @Test
    public void testUpdate() {
        GuestBook guestBook = guestBookRepository.findById(227L).get();
        guestBook.setTitle("02-27");
        guestBook.setContent("내 생일");
        System.out.println(guestBookRepository.save(guestBook));
    }

    @Test
    public void testDelete() {
        guestBookRepository.deleteById(227L);
    }
}
