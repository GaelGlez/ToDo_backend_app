package com.itesm.application.usecase.list;

import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.list_model.ListView;
import com.itesm.domain.repository.ListRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class FindListByIDUseCase {
    private final ListRepository listRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public FindListByIDUseCase(ListRepository listRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.listRepository = listRepository;
        this.authenticatedUserContext= authenticatedUserContext;
    }

    public ListView execute(UUID listId) {
        UUID userId = authenticatedUserContext.getCurrentUser().getUserId();
        return listRepository.findListById(listId, userId);
    }
}
