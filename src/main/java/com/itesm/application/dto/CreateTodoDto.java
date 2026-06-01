package com.itesm.application.dto;

import com.itesm.domain.models.todo_model.TodoPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public class CreateTodoDto {
    @NotNull(message = "listId is required")
    private UUID listId;

    @NotBlank(message = "title is required")
    @Size(max = 150, message = "title must be at most 150 characters")
    private String title;

    @Size(max = 500, message = "description must be at most 500 characters")
    private String description;

    private TodoPriority priority;

    @NotNull(message = "dueDate is required")
    private LocalDate  dueDate;

    public CreateTodoDto() {}

    public CreateTodoDto(String title, String description, TodoPriority priority, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
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

    public UUID getListId() { return listId; }
    public void setListId(UUID listId) { this.listId = listId; }
}
