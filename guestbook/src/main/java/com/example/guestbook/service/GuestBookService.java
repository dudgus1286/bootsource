package com.example.guestbook.service;

import java.util.List;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;

public interface GuestBookService {
    // 페이지 나누기 전
    // public List<GuestBookDto> getList();
    public PageResultDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto);

    public GuestBookDto getRow(Long gno);

    void modify(GuestBookDto dto);

    void remove(Long gno);

    Long create(GuestBookDto dto);

    public default GuestBookDto entityToDto(GuestBook entity) {
        GuestBookDto dto = GuestBookDto.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .updateDate(entity.getUpdateDate())
                .build();
        return dto;
    }

    public default GuestBook dtoToEntity(GuestBookDto dto) {
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
}
