package com.itesm.infrastructure.persistence.repository;

import com.itesm.domain.models.catalog_model.CatalogItem;
import com.itesm.domain.repository.CatalogRepository;
import com.itesm.infrastructure.persistence.entity.CategoryEntity;
import com.itesm.infrastructure.persistence.entity.ColorEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CatalogRepositoryImpl implements CatalogRepository {

    @Inject
    EntityManager em;

    @Override
    public List<CatalogItem> findAllColors() {
        return em.createQuery(
                        "SELECT c FROM ColorEntity c ORDER BY c.name ASC",
                        ColorEntity.class
                )
                .getResultList()
                .stream()
                .map(c -> new CatalogItem(c.getId(), c.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CatalogItem> findAllCategories() {
        return em.createQuery(
                        "SELECT c FROM CategoryEntity c ORDER BY c.name ASC",
                        CategoryEntity.class
                )
                .getResultList()
                .stream()
                .map(c -> new CatalogItem(c.getId(), c.getName()))
                .collect(Collectors.toList());
    }
}
