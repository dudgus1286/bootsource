package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Todo;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // completed 컬럼 값에 따라 조회
    List<Todo> findByCompleted(Boolean completed);

    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.query-methods.query-creation
    // where completed = ? order by todo_id desc
    List<Todo> findByCompletedOrderByIdDesc(Boolean completed);

    // important 컬럼 값에 따라 조회
    List<Todo> findByImportant(Boolean important);

}
