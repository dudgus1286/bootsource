package com.example.todo.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.Todo;

@SpringBootTest
public class TodoRepositoryTest {
    // m2의 DAO == TodoRepository
    // service 에서 호출하는 메소드 테스트

    @Autowired
    private TodoRepository todoRepository;

    // @Test
    // // 테스트용 todo 삽입
    // public void insertTest() {
    // for (int i = 0; i < 10; i++) {
    // Todo todo = Todo.builder().title("title " +
    // i).completed(false).important(false).build();
    // todoRepository.save(todo);
    // }
    // }

    @Test
    // 테스트용 todo 삽입
    public void insertTest() {
        // 엔티티에 @DynamicInsert 어노테이션 추가 + completed, important 필드에 @ColumnDefault 값 추가
        for (int i = 2; i < 11; i++) {
            Todo todo = Todo.builder().title("title " +
                    i).completed(false).important(false).build();
            todoRepository.save(todo);
        }

    }

    @Test
    // todo 전체 목록 조회
    public void readAll() {
        todoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    @Test
    // 하나만 조회
    public void todoRow() {
        System.out.println(todoRepository.findById(1L).get());

    }

    @Test
    // todo 완료 목록 조회
    public void todoCompletedList() {
        todoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));
    }

    @Test
    // todo 주요 목록 조회
    public void todoImportantList() {
        todoRepository.findByImportant(true).forEach(todo -> System.out.println(todo));
    }

    @Test
    // todo 수정
    public void todoUpdate() {
        Todo todo;
        // (제목)
        todo = todoRepository.findById(3L).get();
        todo.setTitle("잠자기");
        todoRepository.save(todo);

        // (완료)
        todo = todoRepository.findById(6L).get();
        todo.setCompleted(true);
        todoRepository.save(todo);

        // (중요)
        todo = todoRepository.findById(7L).get();
        todo.setImportant(true);
        todoRepository.save(todo);

    }

    @Test
    // todo 삭제
    public void todoDelete() {
        todoRepository.deleteById(10L);
    }
}
