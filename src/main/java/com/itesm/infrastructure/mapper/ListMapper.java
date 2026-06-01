package com.itesm.infrastructure.mapper;

import com.itesm.domain.models.list_model.ListTodo;
import com.itesm.infrastructure.persistence.entity.ColorEntity;
import com.itesm.infrastructure.persistence.entity.ListEntity;
import com.itesm.infrastructure.persistence.entity.UserEntity;

public class ListMapper {

    public static ListEntity toEntity(ListTodo list, UserEntity userEntity, ColorEntity colorEntity) {
        ListEntity entity = new ListEntity();

        entity.setId(list.getUuid());
        entity.setUser(userEntity);
        entity.setColor(colorEntity);

        entity.setTitle(list.getTitle());
        entity.setDescription(list.getDescription());

        return entity;
    }

    public static ListTodo toDomain(ListEntity entity) {
        ListTodo list = new ListTodo();

        list.setUuid(entity.getId());
        list.setUserId(entity.getUser().getId());
        list.setColorId(entity.getColor().getId());

        list.setTitle(entity.getTitle());
        list.setDescription(entity.getDescription());
        return list;
    }
}