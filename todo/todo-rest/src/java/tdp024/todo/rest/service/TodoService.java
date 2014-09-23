package tdp024.todo.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import tdp024.todo.data.api.entity.Todo;
import tdp024.todo.data.db.facade.TodoEntityFacadeDB;
import tdp024.todo.logic.api.facade.TodoLogicFacade;
import tdp024.todo.logic.impl.facade.TodoLogicFacadeImpl;
import tdp024.util.json.TodoJsonSerializer;
import tdp024.util.json.TodoJsonSerializerImpl;
import tdp024.util.logger.TodoLogger;
import tdp024.util.logger.TodoLoggerImpl;

@Path("/todo")
public class TodoService {

    // --- Here we choose the implementations of the logic and data layer --- //
    private final TodoLogicFacade todoLogicFacde =
            new TodoLogicFacadeImpl(new TodoEntityFacadeDB());
    //----------------------------------------------------------------------- //

    private static final TodoLogger todoLogger = new TodoLoggerImpl();
    private static final TodoJsonSerializer jsonSerializer = new TodoJsonSerializerImpl();

    @GET
    @Path("create")
    public Response create(
            @QueryParam("title") String title,
            @QueryParam("body") String body) {

        long id = todoLogicFacde.create(title, body);
        return Response.ok().entity(id + "").build();


    }

    @GET
    @Path("find")
    public Response find(
            @QueryParam("id") long id) {

        Todo todo = todoLogicFacde.find(id);

        if (todo != null) {

            String json = jsonSerializer.toJson(todo);

            return Response.status(Response.Status.OK).entity(json).build();

        } else {

            todoLogger.log(
                    TodoLogger.TodoLoggerLevel.WARNING,
                    "No Todo found for id",
                    "We could not find a Todo for id: " + id);

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
}
