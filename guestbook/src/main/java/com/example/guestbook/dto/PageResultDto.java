package com.example.guestbook.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class PageResultDto<DTO, EN> {
    // entity 타입의 리스트를 dto 타입 리스트로 변환
    private List<DTO> dtoList;

    // 화면에서 시작 페이지 번호
    // 화면에서 끝 페이지 번호
    private int start, end;

    // 이전/ 다음 이동 링크 여부
    private boolean prev, next;

    // 현재 페이지 번호
    private int page;

    // 총 페이지의 수
    private int totalPage;

    // 목록의 크기
    private int size;

    // 페이지 번호 목록
    private List<Integer> pageList;

    // 생성자
    // Page<EN> result : 스프링에서 페이지 나누기와 관련된 모든 정보를 가지고 있는 객체
    // Function<EN, DTO> fn : EN 타입을 받아서 DTO 타입으로 변환
    public PageResultDto(Page<EN> result, Function<EN, DTO> fn) {
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());

        this.totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    public void makePageList(Pageable pageable) {
        // Spring 페이지는 0 부터 시작
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        this.start = tempEnd - 9;
        this.end = totalPage > tempEnd ? tempEnd : totalPage;

        this.prev = start > 1;
        this.next = totalPage > tempEnd;

        // .boxed() : int 타입을 Integer 타입으로 변환해서 담음
        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
