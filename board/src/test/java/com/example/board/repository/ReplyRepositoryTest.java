package com.example.board.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.entity.Board;
import com.example.board.entity.Reply;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long bno = (long) (Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder().text("Reply..." + i).replyer("guest" + i).board(board).build();
            replyRepository.save(reply);
        });
    }

    @Transactional
    @Test
    public void getRow() {
        Reply reply = replyRepository.findById(2L).get();
        // FetchType.LAZY 로 설정했기 때문에 @Transactional 없이 reply의 부모 게시물을 가져올 수 없음
        System.out.println(reply.getBoard());
    }

    @Test
    public void getReplies() {
        Board board = Board.builder().bno(1L).build();
        List<Reply> replies = replyRepository.getRepliesByBoardOrderByRno(board);

        System.out.println(replies.toString());
    }
}
