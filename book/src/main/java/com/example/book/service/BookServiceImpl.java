package com.example.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDto;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;
import com.example.book.repository.CategoryRepository;
import com.example.book.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    @Override
    public List<BookDto> getList() {
        // List<BookDto> list = new ArrayList<>();
        // bookRepository.findAll().forEach(book -> list.add(entityToDto(book)));

        // Sort.by("id").descending() 정렬 지정
        List<BookDto> list = bookRepository.findAll(Sort.by("id").descending())
                .stream()
                .map(book -> entityToDto(book))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public Long bookCreate(BookDto dto) {
        Book book = dtoToEntity(dto);
        book.setCategory(categoryRepository.findByName(dto.getCategoryName()).get());
        book.setPublisher(publisherRepository.findByName(dto.getPublisherName()).get());

        Book newBook = bookRepository.save(book);
        return newBook.getId();
    }

    @Override
    public List<String> categoryNameList() {
        return categoryRepository.findAll().stream().map(entity -> entity.getName()).collect(Collectors.toList());
    }

    @Override
    public BookDto getRow(Long id) {
        return entityToDto(bookRepository.findById(id).get());
    }

    @Override
    public Long priceUpdate(BookDto dto) {
        Book entity = bookRepository.findById(dto.getId()).get();
        entity.setPrice(dto.getPrice());
        entity.setSalePrice(dto.getSalePrice());
        Book result = bookRepository.save(entity);
        return result.getId();
    }

    @Override
    public void bookDelete(Long id) {
        bookRepository.deleteById(id);
    }

}
