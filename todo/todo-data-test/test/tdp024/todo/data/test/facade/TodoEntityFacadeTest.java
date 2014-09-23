package tdp024.todo.data.test.facade;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import tdp024.todo.data.api.entity.Todo;
import tdp024.todo.data.api.facade.TodoEntityFacade;
import tdp024.todo.data.api.util.StorageFacade;
import tdp024.todo.data.db.facade.TodoEntityFacadeDB;
import tdp024.todo.data.db.util.StorageFacadeDB;

public class TodoEntityFacadeTest {

    //------------------ Units under test ------------------------------------//
    // We can easily swap between the DB and the flatfile implementations
    // ---- Please experiment with the different implementations ---- 
    private TodoEntityFacade todoEntityFacade = new TodoEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();
    //------------------------------------------------------------------------//

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreate() {


        String title = "First thing to do";
        String body = "Brush your teeth";

        long id = todoEntityFacade.create(title, body);

        Todo todo = todoEntityFacade.find(id);

        Assert.assertEquals(title, todo.getTitle());
        Assert.assertEquals(body, todo.getBody());

    }

    @Test
    public void testActivate() {


        String title = "First thing to do";
        String body = "Brush your teeth";

        long id = todoEntityFacade.create(title, body);

        {
            Todo todo = todoEntityFacade.find(id);
            Assert.assertEquals(false, todo.isActive());
        }

        {
            todoEntityFacade.setActive(id, true);
            Todo todo = todoEntityFacade.find(id);
            Assert.assertEquals(true, todo.isActive());
        }
        {
            todoEntityFacade.setActive(id, false);
            Todo todo = todoEntityFacade.find(id);
            Assert.assertEquals(false, todo.isActive());
        }

    }

    @Test
    public void testNonExistingId() {

        Todo todo = todoEntityFacade.find(0);

        Assert.assertNull(todo);

    }

    @Test
    public void testFindAll() {

        todoEntityFacade.create("First Title", "First Body");
        todoEntityFacade.create("Second Title", "Second Body");
        todoEntityFacade.create("Third Title", "Third Body");

        List<Todo> todos = todoEntityFacade.findAll();

        Assert.assertEquals(3, todos.size());
        
        List<String> titles = new ArrayList<String>();
        titles.add(todos.get(0).getTitle());
        titles.add(todos.get(1).getTitle());
        titles.add(todos.get(2).getTitle());
        
        Assert.assertTrue(titles.contains("First Title"));
        Assert.assertTrue(titles.contains("Second Title"));
        Assert.assertTrue(titles.contains("Third Title"));

    }
}