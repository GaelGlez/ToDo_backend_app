package com.itesm.application.usecase.todo;

import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.todo_model.TodoView;
import com.itesm.domain.repository.TodoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FindAllTodosGraphUseCase {

    private final TodoRepository todoRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public FindAllTodosGraphUseCase(TodoRepository todoRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.todoRepository = todoRepository;
        this.authenticatedUserContext= authenticatedUserContext;
    }

// "entity-graph", "@NamedEntityGraph('Todo.full') como hint — Hibernate arma los joins automáticamente. " +
//  Más declarativo que JPQL manual, reutilizable entre queries.",
    public List<TodoView> execute() {
        UUID userId = authenticatedUserContext.getCurrentUser().getUserId();
        return todoRepository.findAllTodosGraph(userId);
    }
}
