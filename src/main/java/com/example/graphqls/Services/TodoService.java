package com.example.graphqls.Services;

import com.example.graphqls.entity.Todo;
import com.example.graphqls.exceptions.TodoNotFoundException;
import com.example.graphqls.repository.TodoRepository;
import graphql.GraphQLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    @Transactional
    public Todo createTodo(String text) {
        log.info("createTodo({}) called", text);
        return todoRepository.save(Todo.builder().text(text).build());
    }

    @Transactional
    public List<Todo> deleteAllTodos() {
        log.info("deleteAllTodos() called");

        List<Todo> todoList = getTodos(0L);
        todoRepository.deleteAll();
        return todoList;
    }

    @Transactional
    public Todo deleteTodoById(Long id) {
        log.info("deleteTodo({}) called", id);
        return deleteTodo(getTodo(id)
                .orElseThrow(buildNotFoundException(id)));
    }

    private Todo deleteTodo(Todo todo) {
        todoRepository.delete(todo);
        return todo;
    }

    @Transactional
    public Todo toggleTodoById(Long id) {
        log.info("toggleTodoCompleted({}) called", id);
        return getTodo(id)
                .orElseThrow(buildNotFoundException(id));
    }

    private Supplier<GraphQLException> buildNotFoundException(Long id) {
        return () -> new TodoNotFoundException(id);
    }

    @Transactional(readOnly = true)
    public Optional<Todo> getTodo(Long id) {
        log.info("getTodo({}) called", id);
        return todoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Todo> getTodos(Long limit) {
        log.info("getTodos({}) called", limit);
        return todoRepository.findAll()
                .stream()
                .limit(limit > 0 ? limit : Long.MAX_VALUE)
                .collect(toList());
    }
}
