package com.itesm.application.usecase.user;

import com.google.firebase.auth.FirebaseAuthException;
import com.itesm.application.dto.UpdateUserDto;
import com.itesm.application.service.FirebaseUserService;
import com.itesm.domain.models.user_model.User;
import com.itesm.domain.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class UpdateUserUseCase {

    private final UserRepository userRepository;
    private final FirebaseUserService firebaseUserService;

    @Inject
    public UpdateUserUseCase(
            UserRepository userRepository,
            FirebaseUserService firebaseUserService
    ) {
        this.userRepository = userRepository;
        this.firebaseUserService = firebaseUserService;
    }

    public User execute(UUID userId, UpdateUserDto updateUserDto) throws FirebaseAuthException {
        User currentUser = userRepository.findUserById(userId);

        if (currentUser == null) {
            throw new RuntimeException("User no encontrado");
        }

        firebaseUserService.updateUser(
                currentUser.getFirebaseUuid(),
                updateUserDto.getEmail(),
                updateUserDto.getFullName()
        );

        User user = new User();
        user.setFullName(updateUserDto.getFullName());
        user.setEmail(updateUserDto.getEmail());
        user.setRole(updateUserDto.getRole());

        if (updateUserDto.getActive() != null) {
            user.setActive(updateUserDto.getActive());
        } else {
            user.setActive(currentUser.isActive());
        }

        return userRepository.updateUser(userId, user);
    }
}