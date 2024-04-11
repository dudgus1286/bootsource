package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertTest() {
        // LongStream.range(1, 100); // 1 ~ 99까지
        // LongStream.rangeClosed(1, 100); // 1 ~ 100까지
        LongStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("Title...." + i)
                    .content("Content...." + i)
                    .writer("user" + (i % 10))
                    .build();
            boardRepository.save(board);
        });
    }

    @Test
    public void readTest() {
        System.out.println(boardRepository.findById(5L).get());
    }

    @Test
    public void getListTest() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void updateTest() {
        Optional<Board> result = boardRepository.findById(26L);
        // Board board = result.get();
        result.ifPresent(board -> {
            board.setTitle("Update Title....");
            board.setContent("Update Content");
            System.out.println(boardRepository.save(board));
        });
    }

    @Test
    public void deleteTest() {
        // entity 찾기
        Optional<Board> result = boardRepository.findById(66L);

        // 삭제
        boardRepository.delete(result.get());
        // 삭제 확인
        System.out.println("삭제 확인 " + boardRepository.findById(66L)); // 삭제 확인 Optional.empty
    }

    @Test
    public void queryMethodTest() {
        // where b1_0.title=? (결과 없음)
        // List<Board> list = boardRepository.findByTitle("Title");
        // System.out.println(list.size());

        // // b1_0.title like ? escape '\'
        // list = boardRepository.findByTitleLike("Title");
        // System.out.println(list.size());
        // // Title%
        // list = boardRepository.findByTitleStartingWith("Title");
        // System.out.println(list.size());
        // // %Title
        // list = boardRepository.findByTitleEndingWith("Title");
        // System.out.println(list.size());
        // // %Title%
        // list = boardRepository.findByTitleContaining("Title");
        // System.out.println(list.size());

        // list = boardRepository.findByWriterStartingWith("user");
        // System.out.println("findByWriterStartingWith " + list.size());

        // // b1_0.title like ? escape '\' or b1_0.content=?
        // List<Board> list = boardRepository.findByTitleContainingOrContent("Title",
        // "Content");
        // System.out.println(list.size());
        // // b1_0.title like ? escape '\' or b1_0.content like ? escape '\'
        // list = boardRepository.findByTitleContainingOrContentContaining("Title",
        // "Content");
        // System.out.println(list.size());

        // b1_0.title like ? escape '\' and b1_0.id>?
        // List<Board> list =
        // boardRepository.findByTitleContainingAndIdGreaterThan("Title", 50L);
        // System.out.println(list.size());

        // b1_0.id>? order by b1_0.id desc
        // List<Board> list = boardRepository.findByIdGreaterThanOrderByIdDesc(50L);
        // System.out.println(list.size());
        // list.forEach(System.out::println);

        // PageRequest.of(페이지번호, 게시물 수) : 페이지 번호는 0 부터 시작
        Pageable pageable = PageRequest.of(0, 10);
        List<Board> list = boardRepository.findByIdGreaterThanOrderByIdDesc(0L,
                pageable);
        list.forEach(System.out::println);
    }
}
