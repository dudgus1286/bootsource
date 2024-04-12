package com.example.book.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

import jakarta.transaction.Transactional;

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

    @Transactional
    @Test
    public void testBookList() {
        List<Book> list = bookRepository.findAll();
        list.forEach(book -> {
            System.out.println(book);
            System.out.println("출판사 " + book.getPublisher().getName());
            System.out.println("분야 " + book.getCategory().getName());
        });
    }

    @Test
    public void testCateNAmeList() {
        List<Category> list = categoryRepository.findAll();
        list.forEach(category -> System.out.println(category));

        // List<String> cateList = new ArrayList<>();
        // list.forEach(category -> cateList.add(category.getName()));
        // cateList.forEach(System.out::println);
        List<String> cateList2 = list.stream().map(category -> category.getName()).collect(Collectors.toList());
        cateList2.forEach(System.out::println);

    }

    @Test
    public void testSearchList() {
        // Spring Data JPA 페이징 처리 객체
        // pageNumber 는 0 부터 시작
        // Pageable pageable = PageRequest.of(0, 10);
        // Pageable pageable = PageRequest.of(0, 10, Direction.DESC);
        // Pageable pageable = PageRequest.of(0, 10, Direction.DESC, "id");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        // Page : 페이지 나누기에 필요한 메소드 제공 == PageDto 와 같은 역할
        Page<Book> result = bookRepository.findAll(bookRepository.makePredicate(), pageable);

        System.out.println("전체 행 수 " + result.getTotalElements());
        System.out.println("필요한 페이지 수 " + result.getTotalPages());
        result.getContent().forEach(book -> System.out.println(book));
    }
}
