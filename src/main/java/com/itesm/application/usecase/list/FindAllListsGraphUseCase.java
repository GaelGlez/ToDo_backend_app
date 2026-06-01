package com.itesm.application.usecase.list;

import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.domain.models.list_model.ListView;
import com.itesm.domain.repository.ListRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FindAllListsGraphUseCase {

    private final ListRepository listRepository;
    private final AuthenticatedUserContext authenticatedUserContext;

    @Inject
    public FindAllListsGraphUseCase(ListRepository listRepository, AuthenticatedUserContext authenticatedUserContext) {
        this.listRepository = listRepository;
        this.authenticatedUserContext= authenticatedUserContext;
    }

// "entity-graph", "@NamedEntityGraph('Todo.full') como hint — Hibernate arma los joins automáticamente. " +
//  Más declarativo que JPQL manual, reutilizable entre queries.",
    public List<ListView> execute() {
        UUID userId = authenticatedUserContext.getCurrentUser().getUserId();
        return listRepository.findAllListsGraph(userId);
    }
}
