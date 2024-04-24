package com.example.board.service;

import java.util.List;

import com.example.board.dto.ReplyDto;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;

public interface ReplyService {
    List<ReplyDto> getReplies(Long bno);

    Long create(ReplyDto dto);

    void remove(Long rno);

    ReplyDto getReply(Long rno);

    Long update(ReplyDto dto);

    // entity => dto
    public default ReplyDto entityToDto(Reply entity) {
        return ReplyDto.builder()
                .rno(entity.getRno())
                .text(entity.getText())
                .writerEmail(entity.getReplyer().getEmail())
                .writerName(entity.getReplyer().getName())
                .bno(entity.getBoard().getBno())
                .regDate(entity.getRegDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }

    public default Reply dtoToEntity(ReplyDto dto) {

        Board board = Board.builder().bno(dto.getBno()).build();
        Member member = Member.builder().email(dto.getWriterEmail()).build();

        return Reply.builder()
                .rno(dto.getRno())
                .board(board)
                .replyer(member)
                .text(dto.getText())
                .build();
    }
}
