package com.itesm.infrastructure.mapper;

import com.itesm.domain.models.todo_model.TodoView;
import com.itesm.infrastructure.persistence.entity.TodoEntity;

public class TodoViewMapper {

    private TodoViewMapper() {}

    public static TodoView toShallowView(TodoEntity t) {
        return toFullView(t);
    }

    public static TodoView toFullView(TodoEntity t) {
        return new TodoView(
                t.getId().toString(),
                t.getList().getId().toString(),
                t.getTitle(),
                t.getDescription(),
                t.getPriority(),
                t.getDueDate(),
                t.isCompleted()
        );
    }
}
