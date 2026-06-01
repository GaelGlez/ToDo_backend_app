package com.itesm.application.dto;

import java.util.UUID;

public class CatalogItemDto {
    private UUID id;
    private String name;

    public CatalogItemDto() {}

    public CatalogItemDto(UUID id, String name) {
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
