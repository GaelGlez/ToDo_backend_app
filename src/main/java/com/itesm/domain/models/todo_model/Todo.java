package com.itesm.domain.models.todo_model;

import java.time.LocalDate;
import java.util.UUID;

public class Todo {
    private UUID id;
    private UUID userId;
    private UUID listId;
    private String title;
    private String description;
    private TodoPriority priority;
    private LocalDate dueDate;
    private boolean completed;

    public Todo(){
    }

    public Todo(UUID id, String title, String description, TodoPriority priority, LocalDate  dueDate, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public UUID getListId() { return listId; }
    public void setListId(UUID listId) { this.listId = listId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

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

    public TodoPriority getPriority() { return priority; }
    public void setPriority(TodoPriority priority) { this.priority = priority; }

    public LocalDate  getDueDate() { return dueDate; }
    public void setDueDate(LocalDate  dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    @Override
    public String toString() {
        return "Todo{" +
                "uuid=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", completed=" + completed +
                '}';
    }
}
