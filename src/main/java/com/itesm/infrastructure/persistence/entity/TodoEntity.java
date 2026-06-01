package com.itesm.infrastructure.persistence.entity;

import com.itesm.domain.models.todo_model.TodoPriority;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="todos")

@NamedEntityGraph(
        name = "Todo.full",
        attributeNodes = {
                @NamedAttributeNode("list")
        }
)

public class TodoEntity
{
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private UUID id;

    /**
     * LAZY por defecto: el owner NO se carga hasta que se accede a él.
     * Si alguien itera N todos y toca getOwner(), se dispara el problema N+1.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "list_id")
    private ListEntity list;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoPriority priority;

    @Column(nullable = false, name="due_date")
    private LocalDate dueDate;

    @Column(nullable = false)
    private boolean completed;

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();


    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
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

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public ListEntity getList() { return list; }
    public void setList(ListEntity list) { this.list = list; }

    public List<CommentEntity> getComments() { return comments; }
    public void setComments(List<CommentEntity> comments) { this.comments = comments; }
}
