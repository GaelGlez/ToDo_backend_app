package com.itesm.domain.repository;

import com.itesm.domain.models.list_model.ListTodo;
import com.itesm.domain.models.list_model.ListView;

import java.util.List;
import java.util.UUID;

public interface ListRepository {
    ListTodo save(ListTodo list);
    ListTodo updateList(UUID listId, UUID userId, ListTodo list);
    ListTodo deleteListById(UUID uuid, UUID userId);
    List<ListView> findAllListsGraph(UUID userId);
    ListView findListById(UUID listId, UUID userId);
    List<ListView> searchLists(UUID userId, String query);
}
