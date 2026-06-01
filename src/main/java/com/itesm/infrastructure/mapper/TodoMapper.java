package com.itesm.infrastructure.mapper;

import com.itesm.domain.models.todo_model.Todo;
import com.itesm.infrastructure.persistence.entity.ListEntity;
import com.itesm.infrastructure.persistence.entity.TodoEntity;
import com.itesm.infrastructure.persistence.entity.UserEntity;

public class TodoMapper {

    public static TodoEntity toEntity(Todo todo, UserEntity userEntity, ListEntity listEntity) {
        TodoEntity todoEntity = new TodoEntity();

        todoEntity.setId(todo.getUuid());
        todoEntity.setUser(userEntity);
        todoEntity.setList(listEntity);

        todoEntity.setTitle(todo.getTitle());
        todoEntity.setDescription(todo.getDescription());
        todoEntity.setPriority(todo.getPriority());
        todoEntity.setDueDate(todo.getDueDate());
        todoEntity.setCompleted(todo.isCompleted());
        return todoEntity;
    }

    public static Todo toDomain(TodoEntity todoEntity) {
        Todo todo = new Todo();

        todo.setUuid(todoEntity.getId());
        todo.setUserId(todoEntity.getUser().getId());
        todo.setListId(todoEntity.getList().getId());

        todo.setTitle(todoEntity.getTitle());
        todo.setDescription(todoEntity.getDescription());
        todo.setPriority(todoEntity.getPriority());
        todo.setDueDate(todoEntity.getDueDate());
        todo.setCompleted(todoEntity.isCompleted());
        return todo;
    }
}
