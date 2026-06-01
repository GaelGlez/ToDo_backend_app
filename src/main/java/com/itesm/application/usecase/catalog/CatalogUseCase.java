package com.itesm.application.usecase.catalog;

import com.itesm.application.dto.CatalogItemDto;
import com.itesm.domain.models.catalog_model.CatalogItem;
import com.itesm.domain.repository.CatalogRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CatalogUseCase {
    private final CatalogRepository catalogRepository;

    @Inject
    public CatalogUseCase(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public List<CatalogItemDto> findAllColors() {
        return toDtos(catalogRepository.findAllColors());
    }

    public List<CatalogItemDto> findAllCategories() {
        return toDtos(catalogRepository.findAllCategories());
    }

    private List<CatalogItemDto> toDtos(List<CatalogItem> items) {
        return items.stream()
                .map(item -> new CatalogItemDto(item.getId(), item.getName()))
                .collect(Collectors.toList());
    }
}
