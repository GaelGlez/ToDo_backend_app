package com.itesm.application.dto;

import com.itesm.domain.models.user_model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserDto {
    @Size(max = 255, message = "fullName must be at most 255 characters")
    private String fullName;

    @Email(message = "email must have a valid format")
    @Size(max = 255, message = "email must be at most 255 characters")
    private String email;

    private Boolean active;
    private UserRole role;

    public UpdateUserDto() {}

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
