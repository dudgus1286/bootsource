package com.example.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jpa.dto.MemoDto;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // @NotNull, final 이 붙은 멤버변수를 대상으로 생성자 생성
@Service
public class MemoServiceImpl {

    // @RequiredArgsConstructor 없이 멤버변수에 @Autowired 써도 됨
    // @Autowired
    // private MemoRepository memoRepository;

    // private MemoRepository memoRepository = new MemoRepository(); - 스프링 컨테이너가
    // 기본제공하는 기능

    // public MemoServiceImpl(MemoRepository memoRepository) {
    // this.memoRepository = memoRepository;
    // } == @Autowired 의 기능

    private final MemoRepository memoRepository;

    public List<MemoDto> getMemoList() {
        List<Memo> entites = memoRepository.findAll();

        List<MemoDto> list = new ArrayList<>();
        entites.forEach(entity -> list.add(entityToDto(entity)));
        return list;
    }

    public MemoDto getMemo(Long mno) {
        Memo entity = memoRepository.findById(mno).get();

        return entityToDto(entity);
    }

    public MemoDto updateMemo(MemoDto mDto) {
        // 업데이트 대상 찾기
        Memo entity = memoRepository.findById(mDto.getMno()).get();
        // 변경
        entity.setMemoText(mDto.getMemoText());
        return entityToDto(memoRepository.save(entity));
    }

    public void deleteMemo(Long mno) {
        // Memo entity = memoRepository.findById(mno).get();
        // memoRepository.delete(entity);
        memoRepository.deleteById(mno);
    }

    public void createMemo(MemoDto mDto) {
        // dto ==> entity;
        memoRepository.save(dtoToEntity(mDto));
    }

    private MemoDto entityToDto(Memo entity) {
        MemoDto mDto = MemoDto.builder()
                .mno(entity.getMno())
                .memoText(entity.getMemoText())
                .build();

        return mDto;
    }

    private Memo dtoToEntity(MemoDto mDto) {
        Memo entity = Memo.builder()
                .mno(mDto.getMno())
                .memoText(mDto.getMemoText())
                .build();

        return entity;
    }

}
