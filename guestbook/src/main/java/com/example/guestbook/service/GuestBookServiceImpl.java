package com.example.guestbook.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.repository.GuestBookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository guestBookRepository;

    @Override
    public List<GuestBookDto> getList() {
        // List<GuestBookDto> dtoList = new ArrayList<>();
        // guestBookRepository.findAll().forEach(entity ->
        // dtoList.add(entityToDto(entity)));
        // return dtoList;

        List<GuestBook> entities = guestBookRepository.findAll();
        Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));
        return entities.stream().map(fn).collect(Collectors.toList());
    }

}
