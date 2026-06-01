package com.itesm.application.usecase.todo;

import com.itesm.application.dto.UpdateTodoDto;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.todo_model.Todo;
import com.itesm.domain.repository.TodoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class UpdateTodoUseCase {

    private final TodoRepository todoRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public UpdateTodoUseCase(TodoRepository todoRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.todoRepository = todoRepository;
        this.authenticatedUserContext = authenticatedUserContext;
    }

    public Todo execute(UUID todoId, UpdateTodoDto updateTodoDto) {
        UUID userId = authenticatedUserContext.getCurrentUser().getUserId();
        Todo todo = new Todo();

        todo.setListId(updateTodoDto.getListId());
        todo.setTitle(updateTodoDto.getTitle());
        todo.setDescription(updateTodoDto.getDescription());
        todo.setPriority(updateTodoDto.getPriority());
        todo.setDueDate(updateTodoDto.getDueDate());
        return todoRepository.updateTodo(todoId, userId, todo);
    }
}
