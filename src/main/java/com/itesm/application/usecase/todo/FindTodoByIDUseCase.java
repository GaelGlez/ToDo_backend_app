package com.itesm.application.usecase.todo;

import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.todo_model.TodoView;
import com.itesm.domain.repository.TodoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class FindTodoByIDUseCase {
    private final TodoRepository todoRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public FindTodoByIDUseCase(TodoRepository todoRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.todoRepository = todoRepository;
        this.authenticatedUserContext= authenticatedUserContext;
    }

    public TodoView execute(UUID todoId) {
        UUID userId = authenticatedUserContext.getCurrentUser().getUserId();
        return todoRepository.findTodoById(todoId, userId);
    }
}
