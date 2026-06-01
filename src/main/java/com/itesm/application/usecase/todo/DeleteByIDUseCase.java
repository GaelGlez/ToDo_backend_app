package com.itesm.application.usecase.todo;

import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.todo_model.Todo;
import com.itesm.domain.repository.TodoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class DeleteByIDUseCase {
    private final TodoRepository todoRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public DeleteByIDUseCase(TodoRepository todoRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.todoRepository = todoRepository;
        this.authenticatedUserContext = authenticatedUserContext;
    }

    public Todo execute(UUID todoId) {
        UUID userId = authenticatedUserContext.getCurrentUser().getUserId();
        return todoRepository.deleteTodoById(todoId, userId);
    }
}
