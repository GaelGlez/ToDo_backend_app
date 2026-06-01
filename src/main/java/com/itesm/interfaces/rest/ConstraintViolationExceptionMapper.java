package com.itesm.interfaces.rest;

import com.itesm.application.dto.ApiErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        if (message.isBlank()) {
            message = "Invalid request";
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ApiErrorResponse(message, "BAD_REQUEST", 400))
                .build();
    }
}
