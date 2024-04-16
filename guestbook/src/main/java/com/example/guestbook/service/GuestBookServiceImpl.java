package com.example.guestbook.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.entity.QGuestBook;
import com.example.guestbook.repository.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository guestBookRepository;

    // 페이지 나누기 전
    // @Override
    // public List<GuestBookDto> getList() {
    // // List<GuestBookDto> dtoList = new ArrayList<>();
    // // guestBookRepository.findAll().forEach(entity ->
    // // dtoList.add(entityToDto(entity)));
    // // return dtoList;

    // List<GuestBook> entities = guestBookRepository.findAll();
    // Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));
    // return entities.stream().map(fn).collect(Collectors.toList());
    // }

    @Override
    public GuestBookDto getRow(Long gno) {
        return entityToDto(guestBookRepository.findById(gno).get());
    }

    @Override
    public void modify(GuestBookDto dto) {
        GuestBook guestBook = guestBookRepository.findById(dto.getGno()).get();
        guestBook.setTitle(dto.getTitle());
        guestBook.setContent(dto.getContent());
        guestBookRepository.save(guestBook);
    }

    @Override
    public void remove(Long gno) {
        guestBookRepository.deleteById(gno);
    }

    @Override
    public PageResultDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("gno").descending());
        // 페이징 처리하는 PagingAndSortingRepository.findAll(Pageable pageable) - 오버로딩
        // Page<GuestBook> result = guestBookRepository.findAll(pageable);

        BooleanBuilder builder = getSearch(requestDto);
        // querydsl.QuerydslPredicateExecutor.findAll()
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<GuestBookDto, GuestBook>(result, fn);
    }

    @Override
    public Long create(GuestBookDto dto) {
        GuestBook guestBook = guestBookRepository.save(dtoToEntity(dto));
        return guestBook.getGno();
    }

    // Book 프로젝트에서는 BookRepository 에 작성함
    private BooleanBuilder getSearch(PageRequestDto requestDto) {
        BooleanBuilder builder = new BooleanBuilder();

        // Q클래스 사용
        QGuestBook guestbook = QGuestBook.guestBook;

        // type, keyword 가져오기
        String type = requestDto.getType();
        String keyword = requestDto.getKeyword();

        // WHERE gno > 0 AND
        // gno 는 0 보다 크다(PK) 선언
        builder.and(guestbook.gno.gt(0));
        if (type == null || type.trim().length() == 0L) {
            return builder;
        }

        // 검색 타입이 있는 경우
        // title LIKE '%keyword%' OR content LIKE '%keyword%' OR writer LIKE '%keyword%'
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(guestbook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(guestbook.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(guestbook.writer.contains(keyword));
        }
        builder.and(conditionBuilder);

        return builder;
    }
}
