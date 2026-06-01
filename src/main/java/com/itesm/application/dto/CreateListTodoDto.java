package com.itesm.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateListTodoDto {
    @NotBlank(message = "title is required")
    @Size(max = 150, message = "title must be at most 150 characters")
    private String title;

    @Size(max = 500, message = "description must be at most 500 characters")
    private String description;

    @NotNull(message = "colorId is required")
    private UUID colorId;

    private List<UUID> categoryIds;

    public CreateListTodoDto() {}

    public CreateListTodoDto(String title, String description, UUID colorId) {
        this.title = title;
        this.description = description;
        this.colorId = colorId;
        this.categoryIds = new ArrayList<>();
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UUID getColorId() { return colorId; }
    public void setColorId(UUID colorId) { this.colorId = colorId; }

    public List<UUID> getCategoryIds() { return categoryIds; }
    public void setCategoryIds(List<UUID> categoryIds) { this.categoryIds = categoryIds; }
}
