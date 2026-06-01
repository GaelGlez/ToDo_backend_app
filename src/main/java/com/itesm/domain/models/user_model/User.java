package com.itesm.domain.models.user_model;
import java.util.UUID;

public class User {
    private UUID id;
    private String fullName;
    private String email;
    private String firebaseUuid;
    private boolean active;
    private UserRole role;

    public User() {}

    public User(UUID id, String fullName, String email, boolean active, String firebaseUuid, UserRole role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.firebaseUuid = firebaseUuid;
        this.active = active;
        this.role=role;
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

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFirebaseUuid() {
        return firebaseUuid;
    }
    public void setFirebaseUuid(String firebaseUuid) {
        this.firebaseUuid = firebaseUuid;
    }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) {
        this.role = role;
    }
}
