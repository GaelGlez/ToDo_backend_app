package com.itesm.domain.models.todo_model;

import java.time.LocalDate;

public class TodoView {
    private String id;
    private String listId;
    private String title;
    private String description;
    private TodoPriority priority;
    private LocalDate dueDate;
    private boolean completed;

    public TodoView() {}

    public TodoView(
            String id,
            String listId,
            String title,
            String description,
            TodoPriority priority,
            LocalDate dueDate,
            boolean completed
    ) {
        this.id = id;
        this.listId = listId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getListId() { return listId; }
    public void setListId(String listId) { this.listId = listId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TodoPriority getPriority() { return priority; }
    public void setPriority(TodoPriority priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
