package com.itesm.interfaces.rest;

import com.itesm.application.dto.ApiErrorResponse;
import com.itesm.application.dto.CreateTodoDto;
import com.itesm.application.dto.ToggleTodoCompletedDto;
import com.itesm.application.dto.UpdateTodoDto;
import com.itesm.application.usecase.todo.*;
import com.itesm.domain.models.todo_model.Todo;
import com.itesm.domain.models.todo_model.TodoView;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final CreateTodoUseCase createTodoUseCase;
    private final FindTodoByIDUseCase findTodoByIDUseCase;
    private final FindAllTodosGraphUseCase findAllTodosGraphUseCase;
    private final DeleteByIDUseCase deleteByIDUseCase;
    private final UpdateTodoUseCase updateTodoUseCase;
    private final ToggleTodoCompletedUseCase toggleTodoCompletedUseCase;

    @Inject
    public TodoResource(
            CreateTodoUseCase createTodoUseCase,
            FindTodoByIDUseCase findTodoByIDUseCase,
            FindAllTodosGraphUseCase findAllTodosGraphUseCase,
            DeleteByIDUseCase deleteByIDUseCase,
            UpdateTodoUseCase updateTodoUseCase,
            ToggleTodoCompletedUseCase toggleTodoCompletedUseCase
    ) {
        this.createTodoUseCase = createTodoUseCase;
        this.findTodoByIDUseCase = findTodoByIDUseCase;
        this.findAllTodosGraphUseCase = findAllTodosGraphUseCase;
        this.deleteByIDUseCase = deleteByIDUseCase;
        this.updateTodoUseCase = updateTodoUseCase;
        this.toggleTodoCompletedUseCase = toggleTodoCompletedUseCase;
    }

    @POST
    public Response createTodo(@Valid CreateTodoDto createTodoDto){
        try {
            Todo todo = createTodoUseCase.execute(createTodoDto);
            return Response.ok(todo).build();
        } catch (RuntimeException e) {
            return notFound(e.getMessage());
        }
    }

    @GET
    @Path("/graph")
    public Response findAllTodosGraph() {
        List<TodoView> todos = findAllTodosGraphUseCase.execute();
        return Response.ok(todos).build();
    }

    @GET
    @Path("/{id}")
    public Response findTodoById(@PathParam("id") UUID todoId) {
        TodoView todo = findTodoByIDUseCase.execute(todoId);

        if (todo == null) {
            return notFound("Todo not found");
        }

        return Response.ok(todo).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTodo(@PathParam("id") UUID todoId) {
        try {
            Todo deletedTodo = deleteByIDUseCase.execute(todoId);
            return Response.ok(deletedTodo).build();

        } catch (RuntimeException e) {
            return notFound(e.getMessage());
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateTodo(@PathParam("id") UUID todoId, @Valid UpdateTodoDto dto) {
        try {
            Todo updatedTodo = updateTodoUseCase.execute(todoId, dto);
            return Response.ok(updatedTodo).build();

        } catch (RuntimeException e) {
            return notFound(e.getMessage());
        }
    }

    @PATCH
    @Path("/{id}/completed")
    public Response toggleCompleted(
            @PathParam("id") UUID todoId,
            @Valid ToggleTodoCompletedDto dto
    ) {
        try {
            if (dto == null) {
                throw new IllegalArgumentException("completed is required");
            }

            Todo updatedTodo = toggleTodoCompletedUseCase.execute(todoId, dto.getCompleted());
            return Response.ok(updatedTodo).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiErrorResponse(e.getMessage(), "BAD_REQUEST", 400))
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
