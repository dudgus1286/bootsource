package com.example.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDto;
import com.example.board.dto.PageRequestDto;
import com.example.board.dto.PageResultDto;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepository;
import com.example.board.repository.ReplyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Override
    public PageResultDto<BoardDto, Object[]> getList(PageRequestDto requestDto) {

        Page<Object[]> result = boardRepository.list(requestDto.getType(), requestDto.getKeyword(),
                requestDto.getPageable(Sort.by("bno").descending()));

        // List<BoardDto> dtoList = new ArrayList<>();

        // List<Object[]> list = boardRepository.list();
        // for (Object[] objects : list) {
        // Board board = (Board) objects[0];
        // Member member = (Member) objects[1];
        // Long replyCount = (Long) objects[2];
        // dtoList.add(entityToDto(board, member, replyCount));
        // }
        // return dtoList;

        // Page<Object[]> result = boardRepository.list(requestDto);
        Function<Object[], BoardDto> fn = (entity -> entityToDto((Board) entity[0],
                (Member) entity[1],
                (Long) entity[2]));
        // return result.stream().map(fn).collect(Collectors.toList());
        return new PageResultDto<>(result, fn);
    }

    @Override
    public BoardDto getRow(Long bno) {
        Object[] result = boardRepository.getRow(bno);
        BoardDto dto = entityToDto((Board) result[0], (Member) result[1], (Long) result[2]);
        return dto;
    }

    @Override
    public void modify(BoardDto dto) {
        Board board = boardRepository.findById(dto.getBno()).get();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());

        boardRepository.save(board);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        // 자식 삭제
        replyRepository.deleteByBno(bno);

        // 부모 삭제
        boardRepository.deleteById(bno);
    }

    @Override
    public Long insert(BoardDto boardDto) {
        Optional<Member> member = memberRepository.findById(boardDto.getWriterEmail());

        if (member.isPresent()) {
            Board entity = Board.builder()
                    .title(boardDto.getTitle())
                    .content(boardDto.getContent())
                    .writer(member.get())
                    .build();
            entity = boardRepository.save(entity);
            return entity.getBno();
        }
        return null;
    }

}
