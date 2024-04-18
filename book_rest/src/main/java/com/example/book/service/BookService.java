package com.example.book.service;

import java.util.List;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

public interface BookService {
    // 페이지 나누기 전
    // List<BookDto> getList();

    // 페이지 나누기 후
    PageResultDto<BookDto, Book> getList(PageRequestDto requestDto);

    Long bookCreate(BookDto dto);

    List<String> categoryNameList();

    BookDto getRow(Long id);

    Long priceUpdate(BookDto dto);

    void bookDelete(Long id);

    public default BookDto entityToDto(Book entity) {
        BookDto dto = BookDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .writer(entity.getWriter())
                .price(entity.getPrice())
                .salePrice(entity.getSalePrice())
                .createdDate(entity.getCreatedDate())
                .lastModifiedDate(entity.getLastModifiedDate())
                .categoryName(entity.getCategory().getName())
                .publisherName(entity.getPublisher().getName())
                .build();

        return dto;
    }

    public default Book dtoToEntity(BookDto dto) {
        Book entity = Book.builder()
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .price(dto.getPrice())
                .salePrice(dto.getSalePrice())
                .build();

        return entity;

    }
}
