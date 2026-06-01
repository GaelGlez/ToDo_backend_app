package com.itesm.application.usecase.list;

import com.itesm.application.dto.UpdateListDto;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.list_model.ListTodo;
import com.itesm.domain.repository.ListRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class UpdateListUseCase {

    private final ListRepository listRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public UpdateListUseCase(ListRepository listRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.listRepository = listRepository;
        this.authenticatedUserContext = authenticatedUserContext;
    }

    public ListTodo execute(UUID listId, UpdateListDto updateListDto) {
        UUID userId = authenticatedUserContext.getCurrentUser().getUserId();

        ListTodo list = new ListTodo();
        list.setTitle(updateListDto.getTitle());
        list.setDescription(updateListDto.getDescription());
        list.setColorId(updateListDto.getColorId());
        list.setCategoryIds(updateListDto.getCategoryIds());

        return listRepository.updateList(listId, userId, list);
    }
}