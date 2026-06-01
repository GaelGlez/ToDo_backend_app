package com.itesm.application.usecase.search;

import com.itesm.application.dto.SearchResponseDto;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.list_model.ListView;
import com.itesm.domain.models.todo_model.TodoView;
import com.itesm.domain.repository.ListRepository;
import com.itesm.domain.repository.TodoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SearchUseCase {
    private final ListRepository listRepository;
    private final TodoRepository todoRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public SearchUseCase(
            ListRepository listRepository,
            TodoRepository todoRepository,
            AuthenticatedUserContext authenticatedUserContext
    ) {
        this.listRepository = listRepository;
        this.todoRepository = todoRepository;
        this.authenticatedUserContext = authenticatedUserContext;
    }

    public SearchResponseDto execute(String query, String type) {
        UUID userId = authenticatedUserContext.getCurrentUser().getUserId();

        if (query == null || query.trim().isEmpty()) {
            return new SearchResponseDto(List.of(), List.of());
        }

        String normalizedType = type == null ? "all" : type.toLowerCase().trim();
        if (!normalizedType.equals("all") && !normalizedType.equals("lists") && !normalizedType.equals("todos")) {
            throw new IllegalArgumentException("Invalid search type");
        }

        List<ListView> lists = List.of();
        List<TodoView> todos = List.of();

        if (normalizedType.equals("all") || normalizedType.equals("lists")) {
            lists = listRepository.searchLists(userId, query);
        }

        if (normalizedType.equals("all") || normalizedType.equals("todos")) {
            todos = todoRepository.searchTodos(userId, query);
        }

        return new SearchResponseDto(lists, todos);
    }
}
