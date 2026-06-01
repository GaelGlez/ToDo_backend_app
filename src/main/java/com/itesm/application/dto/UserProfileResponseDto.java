package com.itesm.application.dto;

import com.itesm.domain.models.user_model.UserRole;

import java.util.UUID;

public class UserProfileResponseDto {
    private UUID id;
    private String fullName;
    private String email;
    private String firebaseUuid;
    private UserRole role;

    public UserProfileResponseDto(UUID id, String fullName, String email, String firebaseUuid, UserRole role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.firebaseUuid = firebaseUuid;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseUuid() {
        return firebaseUuid;
    }

    public void setFirebaseUuid(String firebaseUuid) {
        this.firebaseUuid = firebaseUuid;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
