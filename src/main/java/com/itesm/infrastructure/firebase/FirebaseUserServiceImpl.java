package com.itesm.infrastructure.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.itesm.application.service.FirebaseUserService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FirebaseUserServiceImpl implements FirebaseUserService {

    @Override
    public void updateUser(String firebaseUuid, String email, String fullName) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(firebaseUuid);

        if (email != null) {
            request.setEmail(email);
        }

        if (fullName != null) {
            request.setDisplayName(fullName);
        }

        FirebaseAuth.getInstance().updateUser(request);
    }

    @Override
    public void disableUser(String firebaseUuid) throws FirebaseAuthException {
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(firebaseUuid)
                .setDisabled(true);

        FirebaseAuth.getInstance().updateUser(request);
    }

    @Override
    public void deleteUser(String firebaseUuid) throws FirebaseAuthException {
        FirebaseAuth.getInstance().deleteUser(firebaseUuid);
    }
}