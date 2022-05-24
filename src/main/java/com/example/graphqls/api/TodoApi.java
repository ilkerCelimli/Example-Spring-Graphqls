package com.example.graphqls.api;

import com.example.graphqls.Services.TodoService;
import com.example.graphqls.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class TodoApi {
    private final TodoService todoService;

    @QueryMapping
    public Optional<Todo> todo(@Argument Long id) {
        return todoService.getTodo(id);
    }

    @QueryMapping
    public List<Todo> todos(@Argument Integer count) {
        return todoService.getTodos(count.longValue());
    }

    @MutationMapping
    public Todo createTodo(@Argument String text) {
        return todoService.createTodo(text);
    }

    @MutationMapping
    public Todo toggleTodo(@Argument Long id) {
        return todoService.toggleTodoById(id);
    }

    @MutationMapping
    public Todo deleteTodo(@Argument Long id) {
        return todoService.deleteTodoById(id);
    }

    @MutationMapping
    public List<Todo> deleteAllTodos() {
        return todoService.deleteAllTodos();
    }
}
