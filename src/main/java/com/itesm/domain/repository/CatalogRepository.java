package com.itesm.domain.repository;

import com.itesm.domain.models.catalog_model.CatalogItem;

import java.util.List;

public interface CatalogRepository {
    List<CatalogItem> findAllColors();
    List<CatalogItem> findAllCategories();
}
