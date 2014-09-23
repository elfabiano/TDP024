package tdpp024.rest.service.test;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.Test;
import tdp024.todo.rest.service.CategoryService;
import tdp024.todo.rest.service.TodoService;
import tdp024.util.json.TodoJsonSerializer;
import tdp024.util.json.TodoJsonSerializerImpl;

public class CategoryServiceTest {

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
    public void testFindByName() {

        CategoryService categoryService = new CategoryService();
        TodoService todoService = new TodoService();

        {
            Response response = categoryService.create("First Category");
            Assert.assertEquals(200, response.getStatus());
        }

        {
            Response response = categoryService.findByName("First Category");
            Assert.assertEquals(200, response.getStatus());

            String json = response.getEntity().toString();

            ObjectNode category = jsonSerializer.fromJson(json, ObjectNode.class);
            Assert.assertEquals("First Category", category.get("name").asText());

        }

        {

            Response response = categoryService.findByName("First Category");
            String json = response.getEntity().toString();
            ObjectNode category = jsonSerializer.fromJson(json, ObjectNode.class);

            long firstId = Long.parseLong(todoService.create("First Title", "First Body").getEntity().toString());
            long secondId = Long.parseLong(todoService.create("Second Title", "Second Body").getEntity().toString());

            categoryService.add(category.get("id").asLong(), firstId);
            categoryService.add(category.get("id").asLong(), secondId);

        }

        {

            Response response = categoryService.findByName("First Category");
            String json = response.getEntity().toString();


            ObjectNode objectNode = new TodoJsonSerializerImpl().fromJson(json, ObjectNode.class);
            Assert.assertNotNull(objectNode);

            ArrayNode todos = objectNode.withArray("todos");

            Assert.assertEquals("First Title", todos.get(0).get("title").asText());
            Assert.assertEquals("Second Title", todos.get(1).get("title").asText());

        }



    }
}
