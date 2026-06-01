package com.itesm.application.dto;

import com.itesm.domain.models.todo_model.TodoPriority;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public class UpdateTodoDto {
    private UUID listId;

    @Size(max = 150, message = "title must be at most 150 characters")
    private String title;

    @Size(max = 500, message = "description must be at most 500 characters")
    private String description;

    private TodoPriority priority;
    private LocalDate dueDate;
    private Boolean completed;

    public UpdateTodoDto() {}

    public UpdateTodoDto(String title, String description, TodoPriority priority, LocalDate dueDate, Boolean completed) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public UUID getListId() {
        return listId;
    }

    public void setListId(UUID listId) {
        this.listId = listId;
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

    public TodoPriority getPriority() {
        return priority;
    }

    public void setPriority(TodoPriority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
