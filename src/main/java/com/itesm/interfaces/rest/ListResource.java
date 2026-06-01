package com.itesm.interfaces.rest;

import com.itesm.application.dto.CreateListTodoDto;
import com.itesm.application.dto.ApiErrorResponse;
import com.itesm.application.dto.UpdateListDto;
import com.itesm.application.usecase.list.CreateListUseCase;
import com.itesm.application.usecase.list.DeleteListByIDUseCase;
import com.itesm.application.usecase.list.FindAllListsGraphUseCase;
import com.itesm.application.usecase.list.FindListByIDUseCase;
import com.itesm.application.usecase.list.FindTodosByListUseCase;
import com.itesm.application.usecase.list.UpdateListUseCase;
import com.itesm.domain.models.list_model.ListTodo;
import com.itesm.domain.models.list_model.ListView;
import com.itesm.domain.models.todo_model.TodoView;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/lists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ListResource {

    private final CreateListUseCase createListUseCase;
    private final FindListByIDUseCase findListByIDUseCase;
    private final FindAllListsGraphUseCase findAllListsGraphUseCase;
    private final DeleteListByIDUseCase deleteListByIDUseCase;
    private final UpdateListUseCase updateListUseCase;
    private final FindTodosByListUseCase findTodosByListUseCase;

    @Inject
    public ListResource(
            CreateListUseCase createListUseCase,
            FindListByIDUseCase findListByIDUseCase,
            FindAllListsGraphUseCase findAllListsGraphUseCase,
            DeleteListByIDUseCase deleteListByIDUseCase,
            UpdateListUseCase updateListUseCase,
            FindTodosByListUseCase findTodosByListUseCase
    ) {
        this.createListUseCase = createListUseCase;
        this.findListByIDUseCase = findListByIDUseCase;
        this.findAllListsGraphUseCase = findAllListsGraphUseCase;
        this.deleteListByIDUseCase = deleteListByIDUseCase;
        this.updateListUseCase = updateListUseCase;
        this.findTodosByListUseCase = findTodosByListUseCase;
    }

    @POST
    public Response createList(@Valid CreateListTodoDto dto) {
        try {
            ListTodo list = createListUseCase.execute(dto);
            return Response.ok(list).build();
        } catch (RuntimeException e) {
            return notFound(e.getMessage());
        }
    }

    @GET
    @Path("/graph")
    public Response findAllListsGraph() {
        List<ListView> lists = findAllListsGraphUseCase.execute();
        return Response.ok(lists).build();
    }

    @GET
    @Path("/{id}")
    public Response findListById(@PathParam("id") UUID listId) {
        ListView list = findListByIDUseCase.execute(listId);

        if (list == null) {
            return notFound("List not found");
        }

        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}/todos")
    public Response findTodosByList(@PathParam("id") UUID listId) {
        try {
            List<TodoView> todos = findTodosByListUseCase.execute(listId);
            return Response.ok(todos).build();
        } catch (RuntimeException e) {
            return notFound(e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteList(@PathParam("id") UUID listId) {
        try {
            ListTodo deletedList = deleteListByIDUseCase.execute(listId);
            return Response.ok(deletedList).build();

        } catch (RuntimeException e) {
            return notFound(e.getMessage());
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateList(@PathParam("id") UUID listId, @Valid UpdateListDto dto) {
        try {
            ListTodo updatedList = updateListUseCase.execute(listId, dto);
            return Response.ok(updatedList).build();

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
