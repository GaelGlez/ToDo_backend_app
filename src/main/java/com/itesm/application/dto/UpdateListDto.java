package com.itesm.application.dto;

import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class UpdateListDto {
    @Size(max = 150, message = "title must be at most 150 characters")
    private String title;

    @Size(max = 500, message = "description must be at most 500 characters")
    private String description;

    private UUID colorId;
    private List<UUID> categoryIds;

    public UpdateListDto() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getColorId() {
        return colorId;
    }

    public void setColorId(UUID colorId) {
        this.colorId = colorId;
    }

    public List<UUID> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<UUID> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
