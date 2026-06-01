package com.itesm.infrastructure.persistence.repository;

import com.itesm.domain.models.todo_model.Todo;
import com.itesm.domain.models.todo_model.TodoView;
import com.itesm.domain.repository.TodoRepository;
import com.itesm.infrastructure.mapper.TodoMapper;
import com.itesm.infrastructure.mapper.TodoViewMapper;
import com.itesm.infrastructure.persistence.entity.ListEntity;
import com.itesm.infrastructure.persistence.entity.TodoEntity;
import com.itesm.infrastructure.persistence.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class TodoRepositoryImpl implements TodoRepository, PanacheRepositoryBase<TodoEntity, UUID> {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public Todo save(Todo todo) {
        UserEntity userEntity = em.find(UserEntity.class, todo.getUserId());
        ListEntity listEntity = em.find(ListEntity.class, todo.getListId());

        if (userEntity == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (listEntity == null || !listEntity.getUser().getId().equals(todo.getUserId())) {
            throw new IllegalArgumentException("List not found");
        }

        TodoEntity entity = TodoMapper.toEntity(todo, userEntity, listEntity);
        persist(entity);
        return TodoMapper.toDomain(entity);
    }

    @Override
    public TodoView findTodoById(UUID todoId, UUID userId) {
        EntityGraph<?> graph = em.getEntityGraph("Todo.full");

        List<TodoEntity> result = em.createQuery(
                        "SELECT t FROM TodoEntity t WHERE t.id = :id AND t.user.id = :userId",
                        TodoEntity.class
                )
                .setParameter("id", todoId)
                .setParameter("userId", userId)
                .setHint("jakarta.persistence.loadgraph", graph)
                .getResultList();

        if (result.isEmpty()) {
            return null;
        }

        return TodoViewMapper.toFullView(result.get(0));
    }

    /**
     * @NamedEntityGraph("Todo.full") como hint — alternativa declarativa.
     */
    @Override
    @Transactional
    public List<TodoView> findAllTodosGraph(UUID userId) {
        EntityGraph<?> graph = em.getEntityGraph("Todo.full");

        TypedQuery<TodoEntity> query = em.createQuery(
                "SELECT t FROM TodoEntity t WHERE t.user.id = :userId ORDER BY t.completed ASC, t.dueDate ASC",
                TodoEntity.class
        );

        query.setParameter("userId", userId);
        query.setHint("jakarta.persistence.loadgraph", graph);

        return query.getResultList().stream()
                .map(TodoViewMapper::toFullView)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Todo updateTodo(UUID todoId, UUID userId, Todo todo) {
        TodoEntity entity = findById(todoId);

        if (entity == null || !entity.getUser().getId().equals(userId)) {
            throw new RuntimeException("Todo no encontrado");
        }

        if (todo.getListId() != null) {
            ListEntity listEntity = em.find(ListEntity.class, todo.getListId());

            if (listEntity == null || !listEntity.getUser().getId().equals(userId)) {
                throw new RuntimeException("List not found");
            }

            entity.setList(listEntity);
        }

        if (todo.getTitle() != null) {
            entity.setTitle(todo.getTitle());
        }

        if (todo.getDescription() != null) {
            entity.setDescription(todo.getDescription());
        }

        if (todo.getPriority() != null) {
            entity.setPriority(todo.getPriority());
        }

        if (todo.getDueDate() != null) {
            entity.setDueDate(todo.getDueDate());
        }

        return TodoMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public Todo updateCompleted(UUID todoId, UUID userId, boolean completed) {
        TodoEntity entity = findById(todoId);

        if (entity == null || !entity.getUser().getId().equals(userId)) {
            throw new RuntimeException("Todo no encontrado");
        }

        entity.setCompleted(completed);
        return TodoMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public Todo deleteTodoById(UUID uuid, UUID userId) {
        TodoEntity todoEntity = findById(uuid);

        if (todoEntity == null || !todoEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("Todo no encontrado");
        }

        Todo deletedTodo = TodoMapper.toDomain(todoEntity);

        delete(todoEntity);

        return deletedTodo;
    }

    @Override
    public List<TodoView> findTodosByListId(UUID listId, UUID userId) {
        Long listCount = em.createQuery(
                        "SELECT COUNT(l) FROM ListEntity l WHERE l.id = :listId AND l.user.id = :userId",
                        Long.class
                )
                .setParameter("listId", listId)
                .setParameter("userId", userId)
                .getSingleResult();

        if (listCount == 0) {
            throw new RuntimeException("List not found");
        }

        EntityGraph<?> graph = em.getEntityGraph("Todo.full");

        TypedQuery<TodoEntity> query = em.createQuery(
                "SELECT t FROM TodoEntity t " +
                        "WHERE t.list.id = :listId AND t.user.id = :userId " +
                        "ORDER BY t.completed ASC, t.dueDate ASC",
                TodoEntity.class
        );

        query.setParameter("listId", listId);
        query.setParameter("userId", userId);
        query.setHint("jakarta.persistence.loadgraph", graph);

        return query.getResultList()
                .stream()
                .map(TodoViewMapper::toFullView)
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoView> searchTodos(UUID userId, String query) {
        EntityGraph<?> graph = em.getEntityGraph("Todo.full");
        String normalized = "%" + query.toLowerCase().trim() + "%";

        TypedQuery<TodoEntity> typedQuery = em.createQuery(
                        "SELECT t FROM TodoEntity t " +
                                "WHERE t.user.id = :userId " +
                                "AND (LOWER(t.title) LIKE :query OR LOWER(t.description) LIKE :query) " +
                                "ORDER BY t.dueDate ASC",
                        TodoEntity.class
                )
                .setParameter("userId", userId)
                .setParameter("query", normalized);

        typedQuery.setHint("jakarta.persistence.loadgraph", graph);

        return typedQuery.getResultList()
                .stream()
                .map(TodoViewMapper::toFullView)
                .collect(Collectors.toList());
    }
}
