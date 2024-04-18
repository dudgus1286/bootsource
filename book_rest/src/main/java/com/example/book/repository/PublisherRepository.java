package com.example.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    // 출판사명으로 찾기
    Optional<Publisher> findByName(String name);
}
