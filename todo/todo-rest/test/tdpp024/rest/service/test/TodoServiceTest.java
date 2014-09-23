package tdpp024.rest.service.test;

import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Test;
import tdp024.todo.rest.service.TodoService;
import tdp024.util.json.TodoJsonSerializer;
import tdp024.util.json.TodoJsonSerializerImpl;

public class TodoServiceTest {

    /*
     * These tests simulates what a client would receive when making
     * requests to our REST service.
     * 
     * What we get back is JSON, this is completely decoupled from our Java
     * apis and our implementations, so we need to treat the returning data
     * as simple a JSON object.
     * 
     * We will use the class ObjectNode to handle JSON, please see the examples
     * below or read more about it online.
     * 
     */
    private TodoJsonSerializer jsonSerializer = new TodoJsonSerializerImpl();

    @Test
    public void testCreate()  {

        TodoService todoService = new TodoService();

        String title = "First title";
        String body = "First body";

        Response response = todoService.create(title, body);

        Assert.assertEquals(200, response.getStatus());

    }

    @Test
    public void testCreateAndFind()  {

        TodoService todoService = new TodoService();

        String title = "First title";
        String body = "First body";

        long id = 0;
        {
            Response response = todoService.create(title, body);
            Assert.assertEquals(200, response.getStatus());

            id = Long.parseLong(response.getEntity().toString());
        }


        {
            Response response = todoService.find(id);
            Assert.assertEquals(200, response.getStatus());

            String json = response.getEntity().toString();

            ObjectNode todo = jsonSerializer.fromJson(json, ObjectNode.class);

            Assert.assertEquals(title, todo.get("title").asText());
            Assert.assertEquals(body, todo.get("body").asText());

        }

    }
}