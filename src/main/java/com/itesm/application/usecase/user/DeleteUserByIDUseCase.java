package com.itesm.application.usecase.user;

import com.google.firebase.auth.FirebaseAuthException;
import com.itesm.application.service.FirebaseUserService;
import com.itesm.domain.models.user_model.User;
import com.itesm.domain.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class DeleteUserByIDUseCase {

    private final UserRepository userRepository;
    private final FirebaseUserService firebaseUserService;

    @Inject
    public DeleteUserByIDUseCase(
            UserRepository userRepository,
            FirebaseUserService firebaseUserService
    ) {
        this.userRepository = userRepository;
        this.firebaseUserService = firebaseUserService;
    }

    public User execute(UUID userId) throws FirebaseAuthException {
        User currentUser = userRepository.findUserById(userId);

        if (currentUser == null) {
            throw new RuntimeException("User no encontrado");
        }

        firebaseUserService.disableUser(currentUser.getFirebaseUuid());

        User user = new User();
        user.setActive(false);

        return userRepository.updateUser(userId, user);
    }
}