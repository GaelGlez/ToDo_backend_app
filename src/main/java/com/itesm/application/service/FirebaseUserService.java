package com.itesm.application.service;

import com.google.firebase.auth.FirebaseAuthException;

public interface FirebaseUserService {
    void updateUser(String firebaseUuid, String email, String fullName) throws FirebaseAuthException;

    void disableUser(String firebaseUuid) throws FirebaseAuthException;

    void deleteUser(String firebaseUuid) throws FirebaseAuthException;
}