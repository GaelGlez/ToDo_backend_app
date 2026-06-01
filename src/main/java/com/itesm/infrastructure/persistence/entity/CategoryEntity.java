package com.itesm.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private UUID id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<ListEntity> lists = new HashSet<>();

    public CategoryEntity() {}

    public CategoryEntity(UUID  id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID  getId() { return id; }
    public void setId(UUID  id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<ListEntity> getLists() { return lists; }
    public void setLists(Set<ListEntity> lists) { this.lists = lists; }
}