package com.itesm.interfaces.rest;

import com.google.firebase.auth.FirebaseAuthException;
import com.itesm.application.dto.ApiErrorResponse;
import com.itesm.application.dto.RegisterUserDto;
import com.itesm.application.dto.UpdateUserDto;
import com.itesm.application.dto.UserProfileResponseDto;
import com.itesm.application.usecase.user.*;
import com.itesm.domain.models.user_model.User;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final RegisterUserUseCase registerUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final FindUserByIDUseCase findUserByIDUseCase;
    private final FindAllUsersUseCase findAllUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserByIDUseCase deleteUserByIDUseCase;

    @Inject
    public UserResource(
            RegisterUserUseCase registerUserUseCase,
            GetCurrentUserUseCase getCurrentUserUseCase,
            FindUserByIDUseCase findUserByIDUseCase,
            FindAllUsersUseCase findAllUsersUseCase,
            UpdateUserUseCase updateUserUseCase,
            DeleteUserByIDUseCase deleteUserByIDUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.findUserByIDUseCase = findUserByIDUseCase;
        this.findAllUsersUseCase = findAllUsersUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserByIDUseCase = deleteUserByIDUseCase;
    }

    @POST
    public Response registerUser(@Valid RegisterUserDto registerUserDto) {
        try {
            User user = registerUserUseCase.execute(registerUserDto);
            return Response.ok(user).build();

        } catch (FirebaseAuthException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiErrorResponse("Unable to register user", "BAD_REQUEST", 400))
                    .build();
        }
    }

    @GET
    @Path("/profile")
    public Response getCurrentUser() {
        UserProfileResponseDto user = getCurrentUserUseCase.execute();
        return Response.ok(user).build();
    }

    @GET
    public Response findAllUsers() {
        List<User> users = findAllUsersUseCase.execute();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response findUserById(@PathParam("id") UUID userId) {
        User user = findUserByIDUseCase.execute(userId);

        if (user == null) {
            return notFound("User not found");
        }

        return Response.ok(user).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") UUID userId, @Valid UpdateUserDto dto) {
        try {
            User updatedUser = updateUserUseCase.execute(userId, dto);
            return Response.ok(updatedUser).build();

        } catch (FirebaseAuthException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiErrorResponse("Unable to synchronize user with Firebase", "BAD_REQUEST", 400))
                    .build();

        } catch (RuntimeException e) {
            return notFound(e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") UUID userId) {
        try {
            User deletedUser = deleteUserByIDUseCase.execute(userId);
            return Response.ok(deletedUser).build();

        } catch (FirebaseAuthException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiErrorResponse("Unable to synchronize user with Firebase", "BAD_REQUEST", 400))
                    .build();

        } catch (RuntimeException e) {
            return notFound(e.getMessage());
        }
    }

    private Response notFound(String message) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ApiErrorResponse(message, "NOT_FOUND", 404))
                .build();
    }
}
