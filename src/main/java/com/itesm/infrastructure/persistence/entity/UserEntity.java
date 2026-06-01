package com.itesm.infrastructure.persistence.entity;

import com.itesm.domain.models.user_model.UserRole;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 255, name="full_name")
    private String fullName;

    @Column(nullable = false, length = 255, unique = true )
    private String email;

    @Column(name="firebase_uuid", unique = true, length = 128)
    private String firebaseUuid;

    @Column(nullable = false)
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    public UserEntity(){
    }
    public UserEntity(UUID id, String fullName, String email, String firebaseUuid, boolean active, UserRole role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.firebaseUuid = firebaseUuid;
        this.active = active;
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

    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
}
