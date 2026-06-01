package com.itesm.domain.repository;

import com.itesm.domain.models.todo_model.Todo;
import com.itesm.domain.models.todo_model.TodoView;

import java.util.List;
import java.util.UUID;


public interface TodoRepository {
    Todo save(Todo todo);
    Todo updateTodo(UUID todoId, UUID userId, Todo todo);
    Todo updateCompleted(UUID todoId, UUID userId, boolean completed);
    Todo deleteTodoById(UUID uuid, UUID userId);
    List<TodoView> findAllTodosGraph(UUID userId);
    TodoView findTodoById(UUID todoId, UUID userId);
    List<TodoView> findTodosByListId(UUID listId, UUID userId);
    List<TodoView> searchTodos(UUID userId, String query);
}
