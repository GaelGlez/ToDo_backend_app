package com.itesm.interfaces.rest;

import com.itesm.application.dto.ApiErrorResponse;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ApiErrorResponse("Resource not found", "NOT_FOUND", 404))
                .build();
    }
}
