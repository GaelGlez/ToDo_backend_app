package com.itesm.interfaces.rest;

import com.itesm.application.dto.CatalogItemDto;
import com.itesm.application.usecase.catalog.CatalogUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CatalogResource {

    private final CatalogUseCase catalogUseCase;

    @Inject
    public CatalogResource(CatalogUseCase catalogUseCase) {
        this.catalogUseCase = catalogUseCase;
    }

    @GET
    @Path("/colors")
    public Response getColors() {
        List<CatalogItemDto> colors = catalogUseCase.findAllColors();
        return Response.ok(colors).build();
    }

    @GET
    @Path("/categories")
    public Response getCategories() {
        List<CatalogItemDto> categories = catalogUseCase.findAllCategories();
        return Response.ok(categories).build();
    }
}
