package com.itesm.application.usecase.todo;

import com.itesm.application.dto.CreateTodoDto;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.todo_model.Todo;
import com.itesm.domain.models.todo_model.TodoPriority;
import com.itesm.domain.repository.TodoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class CreateTodoUseCase {
    private final TodoRepository todoRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public CreateTodoUseCase(TodoRepository todoRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.todoRepository = todoRepository;
        this.authenticatedUserContext= authenticatedUserContext;
    }

    public Todo execute(CreateTodoDto createTodoDto) {
        Todo todo = new Todo();

        todo.setUuid(UUID.randomUUID());
        todo.setUserId(authenticatedUserContext.getCurrentUser().getUserId());
        todo.setListId(createTodoDto.getListId());

        todo.setTitle(createTodoDto.getTitle());
        todo.setDescription(createTodoDto.getDescription());
        todo.setPriority(
                createTodoDto.getPriority() != null
                        ? createTodoDto.getPriority()
                        : TodoPriority.low
        );
        todo.setDueDate(createTodoDto.getDueDate());
        todo.setCompleted(false);

        return todoRepository.save(todo);
    }
}
