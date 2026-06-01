package com.itesm.application.dto;

import com.itesm.domain.models.list_model.ListView;
import com.itesm.domain.models.todo_model.TodoView;

import java.util.List;

public class SearchResponseDto {
    private List<ListView> lists;
    private List<TodoView> todos;

    public SearchResponseDto() {}

    public SearchResponseDto(List<ListView> lists, List<TodoView> todos) {
        this.lists = lists;
        this.todos = todos;
    }

    public List<ListView> getLists() {
        return lists;
    }

    public void setLists(List<ListView> lists) {
        this.lists = lists;
    }

    public List<TodoView> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoView> todos) {
        this.todos = todos;
    }
}
