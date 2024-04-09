package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // == @Autowired private TodoRepository todoRepository;
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    @Override
    public List<TodoDto> getList() {
        // 미완료 목록 출력
        List<Todo> list = todoRepository.findByCompletedOrderByIdDesc(false);
        // Todo => TodoDto 로 변환
        // List<TodoDto> todoList = new ArrayList<>();
        // list.forEach(entity -> todoList.add(entityToDto(entity)));

        // 간략화
        List<TodoDto> todoList = list.stream()
                .map(todo -> entityToDto(todo))
                .collect(Collectors.toList());
        return todoList;
    }

    @Override
    public TodoDto create(TodoDto dto) {
        // TodoDto => Todo 변환
        Todo entity = todoRepository.save(dtoToEntity(dto));

        return entityToDto(entity);
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id).get();

        return entityToDto(todo);
    }

    @Override
    public List<TodoDto> getCompletedList() {
        List<TodoDto> list = new ArrayList<>();
        todoRepository.findByCompleted(true)
                .forEach(todo -> list.add(entityToDto(todo)));
        return list;

    }

    @Override
    public Long todoUpdate(long id) {
        Todo entity = todoRepository.findById(id).get();
        entity.setCompleted(true);
        Todo todo = todoRepository.save(entity);
        // 업데이트 완료 후 id만 리턴
        return todo.getId();
    }

    @Override
    public void todoDelete(long id) {
        todoRepository.deleteById(id);
    }

}
