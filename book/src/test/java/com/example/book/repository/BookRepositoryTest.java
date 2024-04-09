package com.example.book.repository;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void insertTest1() {
        categoryRepository.save(Category.builder().name("컴퓨터").build());
        categoryRepository.save(Category.builder().name("경제/경영").build());
        categoryRepository.save(Category.builder().name("인문").build());
        categoryRepository.save(Category.builder().name("소설").build());
        categoryRepository.save(Category.builder().name("자기계발").build());

        publisherRepository.save(Publisher.builder().name("로드북").build());
        publisherRepository.save(Publisher.builder().name("다산").build());
        publisherRepository.save(Publisher.builder().name("웅진지식하우스").build());
        publisherRepository.save(Publisher.builder().name("비룡소").build());
        publisherRepository.save(Publisher.builder().name("을유문화사").build());
    }

    @Test
    public void insertBookTest() {
        LongStream.rangeClosed(1, 10).forEach(n -> {
            bookRepository.save(Book.builder()
                    .title("책 제목 " + n)
                    .writer("저자 " + n)
                    .price((int) (5000 * n))
                    .salePrice((int) (100 * n))
                    .build());
        });
    }

    @Test
    public void updateBookTest() {
        List<Publisher> list = publisherRepository.findAll();
        for (int i = 1; i <= list.size(); i++) {
            Book book = bookRepository.findById((long) (i)).get();
            book.setPublisher(publisherRepository.findById((long) i).get());
            bookRepository.save(book);
        }
    }
}
