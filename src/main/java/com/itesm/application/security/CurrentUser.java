package com.itesm.application.security;

import com.itesm.domain.models.user_model.UserRole;

import java.util.UUID;

public class CurrentUser {
    private final UUID userId;
    private final String firebaseUuid;
    private final String email;
    private final UserRole role;
    private final String fullName;

    public CurrentUser(UUID userId, String firebaseUuid, String email, UserRole role, String fullName) {
        this.userId = userId;
        this.firebaseUuid = firebaseUuid;
        this.email = email;
        this.role = role;
        this.fullName = fullName;
    }

    public boolean hasRole(UserRole role){
        return this.role.equals(role);
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFirebaseUuid() {
        return firebaseUuid;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }
}
