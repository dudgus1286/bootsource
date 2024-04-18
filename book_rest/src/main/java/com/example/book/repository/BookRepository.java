package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.book.entity.Book;
import com.example.book.entity.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {
    public default Predicate makePredicate(String type, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();

        QBook book = QBook.book;
        // id>0
        builder.and(book.id.gt(0L));

        // 검색
        // 검색 타입이 없는 경우
        if (type == null)
            return builder;

        // 검색 타입이 있는 경우
        if (type.equals("c")) {
            builder.and(book.category.name.contains(keyword));
        } else if (type.equals("t")) {
            builder.and(book.title.contains(keyword));
        } else if (type.equals("w")) {
            builder.and(book.writer.contains(keyword));
        }

        return builder;
    }
}
