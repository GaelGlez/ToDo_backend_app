package com.itesm.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "todo_id")
    private TodoEntity todo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @Column(nullable = false, length = 500)
    private String content;

    public CommentEntity() {}

    public CommentEntity(UUID id, String content, LocalDateTime createdAt, TodoEntity todo, UserEntity author) {
        this.id = id;
        this.content = content;
        this.todo = todo;
        this.author = author;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public TodoEntity getTodo() { return todo; }
    public void setTodo(TodoEntity todo) { this.todo = todo; }
    public UserEntity getAuthor() { return author; }
    public void setUser(UserEntity author) { this.author = author; }
}