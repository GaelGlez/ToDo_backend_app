package com.itesm.interfaces.rest;

import com.itesm.application.dto.ApiErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ApiErrorResponse("Internal server error", "INTERNAL_SERVER_ERROR", 500))
                .build();
    }
}
