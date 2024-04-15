package com.example.book.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.Data;

// Java Generics : Box<T> 의 형태
// PageResultDto<DTO, EN> : ~DTO, Entity 객체를 담기 위한 구조 설계

@Data
public class PageResultDto<DTO, EN> {
    // 화면에 보여줄 목록
    private List<DTO> dtoList;

    // 총 페이지 번호
    private int totalPage;

    // 현재 페이지 번호
    private int page;

    // 목록 크기(한 페이지에 보여줄 게시물 수)
    private int size;

    // 시작, 끝 페이지 번호
    private int start, end;

    private boolean prev, next;

    // 페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDto(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        // 총 게시물 개수 / 한 페이지 당 보여줄 게시물 수
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        // pageable.getPageNumber() : 사용자가 요청한 페이지 번호(페이지 번호가 0부터 시작)
        // 실제 페이지는 1 부터 시작하니까 +1
        this.page = pageable.getPageNumber() + 1;
        // 한 페이지 당 보여줄 게시물 수
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        this.start = tempEnd - 9;
        this.prev = start > 1;
        this.end = totalPage > tempEnd ? tempEnd : totalPage;
        this.next = totalPage > tempEnd;

        // int 타입으로 1 ~ 10 생성 ==> List<Integer> list
        // box() : int ==> Integer 로 변환
        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
