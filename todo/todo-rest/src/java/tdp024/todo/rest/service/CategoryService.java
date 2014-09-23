package tdp024.todo.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import tdp024.todo.data.api.entity.Category;
import tdp024.todo.data.api.entity.Todo;
import tdp024.todo.data.db.facade.CategoryEntityFacadeDB;
import tdp024.todo.logic.api.facade.CategoryLogicFacade;
import tdp024.todo.logic.impl.facade.CategoryLogicFacadeImpl;
import tdp024.util.json.TodoJsonSerializer;
import tdp024.util.json.TodoJsonSerializerImpl;
import tdp024.util.logger.TodoLogger;
import tdp024.util.logger.TodoLoggerImpl;

@Path("/category")
public class CategoryService {

    // --- Here we choose the implementations of the logic and data layer --- //
    private final CategoryLogicFacade categoryLogicFacade =
            new CategoryLogicFacadeImpl(new CategoryEntityFacadeDB());
    //----------------------------------------------------------------------- //
    
    private static final TodoLogger todoLogger = new TodoLoggerImpl();
    private static final TodoJsonSerializer jsonSerializer = new TodoJsonSerializerImpl();

    @GET
    @Path("create")
    public Response create(
            @QueryParam("name") String name) {

        categoryLogicFacade.create(name);
        return Response.ok().build();

    }

    @GET
    @Path("add")
    public Response add(
            @QueryParam("categoryId") long categoryId,
            @QueryParam("todoId") long todoId) {

        categoryLogicFacade.addTodo(categoryId, todoId);
        return Response.ok().build();

    }

    @GET
    @Path("find/name")
    public Response findByName(
            @QueryParam("name") String name) {

        Category category = categoryLogicFacade.findByName(name);

        if (category == null) {

            todoLogger.log(TodoLogger.TodoLoggerLevel.WARNING,
                    "No Category found for id",
                    "We could not find a Category for name: " + name);

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        /* Remove circular references before you serialize */
        if (category.getTodos() != null) {
            for (Todo todo : category.getTodos()) {
                todo.setCategory(null);
            }
        }

        String json = jsonSerializer.toJson(category);

        return Response.ok().entity(json).build();

    }
}
