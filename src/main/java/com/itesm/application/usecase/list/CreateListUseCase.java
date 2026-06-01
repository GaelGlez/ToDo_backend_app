package com.itesm.application.usecase.list;

import com.itesm.application.dto.CreateListTodoDto;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.list_model.ListTodo;
import com.itesm.domain.repository.ListRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;


@ApplicationScoped
public class CreateListUseCase {
    private final ListRepository listRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public CreateListUseCase(ListRepository listRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.listRepository = listRepository;
        this.authenticatedUserContext = authenticatedUserContext;
    }

    public ListTodo execute(CreateListTodoDto createListDto) {
        ListTodo list = new ListTodo();

        list.setUuid(UUID.randomUUID());
        list.setUserId(authenticatedUserContext.getCurrentUser().getUserId());
        list.setColorId(createListDto.getColorId());

        list.setTitle(createListDto.getTitle());
        list.setDescription(createListDto.getDescription());
        list.setCategoryIds(createListDto.getCategoryIds());

        return listRepository.save(list);
    }

}
