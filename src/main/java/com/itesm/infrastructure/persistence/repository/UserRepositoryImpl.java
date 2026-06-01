package com.itesm.infrastructure.persistence.repository;

import com.itesm.domain.models.user_model.User;
import com.itesm.domain.repository.UserRepository;
import com.itesm.infrastructure.mapper.UserMapper;
import com.itesm.infrastructure.persistence.entity.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository, PanacheRepositoryBase<UserEntity, UUID> {

    @Override
    public Optional<User> findByFirebaseUuid(String firebaseUuid) {
        return find("firebaseUuid", firebaseUuid)
                .firstResultOptional()
                .map(this::mapToDomain);
    }

    @Override
    @Transactional
    public User create(User user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        persist(userEntity);
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public User findUserById(UUID userId) {
        UserEntity userEntity = findById(userId);

        if (userEntity == null) {
            return null;
        }

        return UserMapper.toDomain(userEntity);
    }

    @Override
    public List<User> findAllUsers() {
        return listAll()
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User updateUser(UUID userId, User user) {
        UserEntity userEntity = findById(userId);

        if (userEntity == null) {
            throw new RuntimeException("User no encontrado");
        }

        if (user.getFullName() != null) {
            userEntity.setFullName(user.getFullName());
        }

        if (user.getEmail() != null) {
            userEntity.setEmail(user.getEmail());
        }

        userEntity.setActive(user.isActive());

        if (user.getRole() != null) {
            userEntity.setRole(user.getRole());
        }

        return UserMapper.toDomain(userEntity);
    }

    @Override
    @Transactional
    public User deleteUserById(UUID userId) {
        UserEntity userEntity = findById(userId);

        if (userEntity == null) {
            throw new RuntimeException("User no encontrado");
        }

        User deletedUser = UserMapper.toDomain(userEntity);

        delete(userEntity);

        return deletedUser;
    }

    private User mapToDomain(UserEntity userEntity) {
        return UserMapper.toDomain(userEntity);
    }
}