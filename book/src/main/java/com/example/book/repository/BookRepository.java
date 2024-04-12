package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.book.entity.Book;
import com.example.book.entity.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {
    public default Predicate makePredicate() {
        BooleanBuilder builder = new BooleanBuilder();

        QBook book = QBook.book;
        // id>0
        builder.and(book.id.gt(0L));

        return builder;
    }
}
