package com.itesm.interfaces.rest;

import com.itesm.application.dto.ApiErrorResponse;
import com.itesm.application.dto.SearchResponseDto;
import com.itesm.application.usecase.search.SearchUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    private final SearchUseCase searchUseCase;

    @Inject
    public SearchResource(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    @GET
    public Response search(
            @QueryParam("q") String query,
            @QueryParam("type") @DefaultValue("all") String type
    ) {
        try {
            SearchResponseDto response = searchUseCase.execute(query, type);
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiErrorResponse(e.getMessage(), "BAD_REQUEST", 400))
                    .build();
        }
    }
}
