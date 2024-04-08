package com.example.todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TodoServiceTest {
    // Service, Service <==> Repository 가 잘 동작하는지 확인
    @Autowired
    private TodoServiceImpl service;

    @Test
    public void serviceList() {
        System.out.println(service.getList());
    }

}
