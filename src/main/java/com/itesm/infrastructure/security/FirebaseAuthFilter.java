package com.itesm.infrastructure.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.itesm.application.dto.ApiErrorResponse;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.application.security.CurrentUser;
import com.itesm.domain.models.user_model.User;
import com.itesm.domain.repository.UserRepository;
import io.quarkus.arc.profile.UnlessBuildProfile;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Optional;

@Provider
@Priority(Priorities.AUTHENTICATION)
@UnlessBuildProfile("test")
public class FirebaseAuthFilter implements ContainerRequestFilter {
    @Inject
    UserRepository userRepository;
    @Inject
    AuthenticatedUserContext authenticatedUserContext;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        String normalizedPath = path.startsWith("/") ? path.substring(1) : path;
        if (normalizedPath.endsWith("/")) {
            normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
        }

        String method = requestContext.getMethod();

        boolean isPublicRegister =
                normalizedPath.equals("user") && method.equalsIgnoreCase("POST");

        boolean isPublicStatus =
                normalizedPath.equals("status") && method.equalsIgnoreCase("GET");

        if (isPublicRegister || isPublicStatus) {
            return;
        }
        String authHeader = requestContext.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(unauthorized());
            return;
        }
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(authHeader.replace("Bearer ", ""), true);
            Optional<User> userOptional = userRepository.findByFirebaseUuid(decodedToken.getUid());
            if (userOptional.isEmpty()) {
                requestContext.abortWith(unauthorized());
                return;
            }
            User user = userOptional.get();
            CurrentUser currentUser = new CurrentUser(
                    user.getId(), user.getFirebaseUuid(), user.getEmail(), user.getRole(), user.getFullName()
            );
            authenticatedUserContext.setCurrentUser(currentUser);
        } catch (FirebaseAuthException e) {
            requestContext.abortWith(unauthorized());
        }

    }

    private Response unauthorized() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ApiErrorResponse("Unauthorized", "UNAUTHORIZED", 401))
                .build();
    }
}
