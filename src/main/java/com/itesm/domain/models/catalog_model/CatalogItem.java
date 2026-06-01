package com.itesm.domain.models.catalog_model;

import java.util.UUID;

public class CatalogItem {
    private UUID id;
    private String name;

    public CatalogItem() {}

    public CatalogItem(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
