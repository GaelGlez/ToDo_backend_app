package com.itesm.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Table(name="lists")

@NamedEntityGraph(
        name = "List.full",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("color"),
                @NamedAttributeNode("todos"),
                @NamedAttributeNode("categories")
        }
)

public class ListEntity
{
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "color_id")
    private ColorEntity color;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "list", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<TodoEntity> todos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "lists_categories",
            joinColumns = @JoinColumn(name = "list_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categories = new HashSet<>();


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ColorEntity getColor() {
        return color;
    }

    public void setColor(ColorEntity color) {
        this.color = color;
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

    public List<TodoEntity> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoEntity> todos) {
        this.todos = todos;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }
}
