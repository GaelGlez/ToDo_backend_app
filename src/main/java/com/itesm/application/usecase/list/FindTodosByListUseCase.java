package com.itesm.application.usecase.list;

import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.todo_model.TodoView;
import com.itesm.domain.repository.TodoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FindTodosByListUseCase {
    private final TodoRepository todoRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public FindTodosByListUseCase(TodoRepository todoRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.todoRepository = todoRepository;
        this.authenticatedUserContext = authenticatedUserContext;
    }

    public List<TodoView> execute(UUID listId) {
        UUID userId = authenticatedUserContext.getCurrentUser().getUserId();
        return todoRepository.findTodosByListId(listId, userId);
    }
}
