package com.example.todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.dto.TodoDto;

@SpringBootTest
public class TodoServiceTest {
    // Service, Service <==> Repository 가 잘 동작하는지 확인
    @Autowired
    private TodoService service;

    @Test
    public void serviceList() {
        System.out.println(service.getList());
    }

    @Test
    public void createTest() {
        TodoDto dto = TodoDto.builder().title("test011").important(false).build();
        System.out.println(service.create(dto));
    }

    @Test
    public void getTodoTest() {
        System.out.println(service.getTodo(3L));
    }

}
