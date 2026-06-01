package com.itesm.infrastructure.mapper;

import com.itesm.domain.models.list_model.ListView;
import com.itesm.infrastructure.persistence.entity.ListEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ListViewMapper {

    private ListViewMapper() {}

    public static ListView toFullView(ListEntity entity) {
        int totalTodos = entity.getTodos().size();

        int completedTodos = (int) entity.getTodos().stream()
                .filter(todo -> todo.isCompleted())
                .count();

        int completionPercentage = totalTodos == 0
                ? 0
                : Math.round((completedTodos * 100f) / totalTodos);

        List<String> categories = entity.getCategories().stream()
                .map(category -> category.getName())
                .collect(Collectors.toList());

        return new ListView(
                entity.getId().toString(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getColor().getName(),
                totalTodos,
                completedTodos,
                completionPercentage,
                categories
        );
    }
}