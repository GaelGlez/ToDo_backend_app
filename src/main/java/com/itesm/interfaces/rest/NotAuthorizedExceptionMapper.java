package com.itesm.interfaces.rest;

import com.itesm.application.dto.ApiErrorResponse;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

    @Override
    public Response toResponse(NotAuthorizedException exception) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ApiErrorResponse("Unauthorized", "UNAUTHORIZED", 401))
                .build();
    }
}
