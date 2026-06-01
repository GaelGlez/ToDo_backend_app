package com.itesm.interfaces.rest;

import com.itesm.application.dto.ApiErrorResponse;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(ForbiddenException exception) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(new ApiErrorResponse("Forbidden", "FORBIDDEN", 403))
                .build();
    }
}
