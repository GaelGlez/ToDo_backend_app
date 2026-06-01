package com.itesm.application.usecase.user;

import com.itesm.domain.models.user_model.User;
import com.itesm.domain.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class FindUserByIDUseCase {

    private final UserRepository userRepository;

    @Inject
    public FindUserByIDUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID userId) {
        return userRepository.findUserById(userId);
    }
}