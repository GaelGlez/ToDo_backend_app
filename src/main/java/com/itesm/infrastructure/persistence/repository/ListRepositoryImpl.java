package com.itesm.infrastructure.persistence.repository;

import com.itesm.domain.models.list_model.ListTodo;
import com.itesm.domain.models.list_model.ListView;
import com.itesm.domain.repository.ListRepository;
import com.itesm.infrastructure.mapper.ListMapper;
import com.itesm.infrastructure.mapper.ListViewMapper;
import com.itesm.infrastructure.persistence.entity.CategoryEntity;
import com.itesm.infrastructure.persistence.entity.ColorEntity;
import com.itesm.infrastructure.persistence.entity.ListEntity;
import com.itesm.infrastructure.persistence.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ListRepositoryImpl implements ListRepository, PanacheRepositoryBase<ListEntity, UUID> {

    @Inject
    EntityManager em;

    @Override
    @Transactional
    public ListTodo save(ListTodo list) {
        UserEntity userEntity = em.find(UserEntity.class, list.getUserId());
        ColorEntity colorEntity = em.find(ColorEntity.class, list.getColorId());

        if (userEntity == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (colorEntity == null) {
            throw new IllegalArgumentException("Color not found");
        }

        ListEntity entity = ListMapper.toEntity(list, userEntity, colorEntity);
        persist(entity);

        if (list.getCategoryIds() != null && !list.getCategoryIds().isEmpty()) {
            entity.setCategories(findCategories(list.getCategoryIds()));
        }

        return ListMapper.toDomain(entity);
    }

    @Override
    public ListView findListById(UUID listId, UUID userId) {
        EntityGraph<?> graph = em.getEntityGraph("List.full");

        List<ListEntity> result = em.createQuery(
                        "SELECT l FROM ListEntity l WHERE l.id = :id AND l.user.id = :userId",
                        ListEntity.class
                )
                .setParameter("id", listId)
                .setParameter("userId", userId)
                .setHint("jakarta.persistence.loadgraph", graph)
                .getResultList();

        if (result.isEmpty()) {
            return null;
        }

        return ListViewMapper.toFullView(result.get(0));
    }

    @Override
    @Transactional
    public List<ListView> findAllListsGraph(UUID userId) {
        EntityGraph<?> graph = em.getEntityGraph("List.full");

        TypedQuery<ListEntity> query = em.createQuery(
                "SELECT DISTINCT l FROM ListEntity l WHERE l.user.id = :userId ORDER BY l.title ASC",
                ListEntity.class
        );

        query.setParameter("userId", userId);
        query.setHint("jakarta.persistence.loadgraph", graph);

        return query.getResultList().stream()
                .map(ListViewMapper::toFullView)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ListTodo updateList(UUID listId, UUID userId, ListTodo list) {
        ListEntity entity = findById(listId);

        if (entity == null || !entity.getUser().getId().equals(userId)) {
            throw new RuntimeException("List no encontrada");
        }

        if (list.getColorId() != null) {
            ColorEntity colorEntity = em.find(ColorEntity.class, list.getColorId());

            if (colorEntity == null) {
                throw new RuntimeException("Color not found");
            }

            entity.setColor(colorEntity);
        }

        if (list.getTitle() != null) {
            entity.setTitle(list.getTitle());
        }

        if (list.getDescription() != null) {
            entity.setDescription(list.getDescription());
        }

        if (list.getCategoryIds() != null) {
            entity.setCategories(findCategories(list.getCategoryIds()));
        }

        return ListMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public ListTodo deleteListById(UUID listId, UUID userId) {
        ListEntity listEntity = findById(listId);

        if (listEntity == null || !listEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("List no encontrada");
        }

        ListTodo deletedList = ListMapper.toDomain(listEntity);

        delete(listEntity);

        return deletedList;
    }

    @Override
    public List<ListView> searchLists(UUID userId, String query) {
        EntityGraph<?> graph = em.getEntityGraph("List.full");
        String normalized = "%" + query.toLowerCase().trim() + "%";

        TypedQuery<ListEntity> typedQuery = em.createQuery(
                "SELECT DISTINCT l FROM ListEntity l " +
                        "WHERE l.user.id = :userId " +
                        "AND (LOWER(l.title) LIKE :query OR LOWER(l.description) LIKE :query) " +
                        "ORDER BY l.title ASC",
                ListEntity.class
        );

        typedQuery.setParameter("userId", userId);
        typedQuery.setParameter("query", normalized);
        typedQuery.setHint("jakarta.persistence.loadgraph", graph);

        return typedQuery.getResultList()
                .stream()
                .map(ListViewMapper::toFullView)
                .collect(Collectors.toList());
    }

    private Set<CategoryEntity> findCategories(List<UUID> categoryIds) {
        Set<UUID> uniqueCategoryIds = new HashSet<>(categoryIds);

        if (uniqueCategoryIds.isEmpty()) {
            return new HashSet<>();
        }

        List<CategoryEntity> categories = em.createQuery(
                        "SELECT c FROM CategoryEntity c WHERE c.id IN :ids",
                        CategoryEntity.class
                )
                .setParameter("ids", uniqueCategoryIds)
                .getResultList();

        if (categories.size() != uniqueCategoryIds.size()) {
            throw new IllegalArgumentException("Category not found");
        }

        return new HashSet<>(categories);
    }
}
