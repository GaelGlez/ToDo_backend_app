package com.itesm.domain.models.list_model;

import java.util.List;
import java.util.UUID;

public class ListTodo {
    private UUID id;
    private UUID userId;
    private UUID colorId;
    private String title;
    private String description;
    private List<UUID> categoryIds;

    public ListTodo(){
    }

    public ListTodo(UUID id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public UUID getColorId() { return colorId; }
    public void setColorId(UUID colorId) { this.colorId = colorId; }

    public List<UUID> getCategoryIds() { return categoryIds; }
    public void setCategoryIds(List<UUID> categoryIds) { this.categoryIds = categoryIds; }

    public UUID getUuid() {
        return id;
    }
    public void setUuid(UUID id) {
        this.id = id;
    }

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


    @Override
    public String toString() {
        return "Todo{" +
                "uuid=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
