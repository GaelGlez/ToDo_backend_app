package com.itesm.domain.models.list_model;

import java.util.List;

public class ListView {
    private String id;
    private String title;
    private String description;
    private String color;
    private int totalTodos;
    private int completedTodos;
    private int completionPercentage;
    private List<String> categories;

    public ListView(
            String id,
            String title,
            String description,
            String color,
            int totalTodos,
            int completedTodos,
            int completionPercentage,
            List<String> categories
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
        this.totalTodos = totalTodos;
        this.completedTodos = completedTodos;
        this.completionPercentage = completionPercentage;
        this.categories = categories;
    }

    // getters y setters

    public String getId() { return id; }
    public void setId(String id) {
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

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public int getTotalTodos() {
        return totalTodos;
    }
    public void setTotalTodos(int totalTodos) {
        this.totalTodos = totalTodos;
    }

    public int getCompletedTodos() {
        return completedTodos;
    }
    public void setCompletedTodos(int completedTodos) {
        this.completedTodos = completedTodos;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }
    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public List<String> getCategories() {
        return categories;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}