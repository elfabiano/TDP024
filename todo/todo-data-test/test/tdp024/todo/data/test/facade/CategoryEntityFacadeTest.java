package tdp024.todo.data.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import tdp024.todo.data.api.entity.Category;
import tdp024.todo.data.api.facade.CategoryEntityFacade;
import tdp024.todo.data.api.facade.TodoEntityFacade;
import tdp024.todo.data.api.util.StorageFacade;
import tdp024.todo.data.db.facade.CategoryEntityFacadeDB;
import tdp024.todo.data.db.facade.TodoEntityFacadeDB;
import tdp024.todo.data.db.util.StorageFacadeDB;

public class CategoryEntityFacadeTest {

    //------------------ Units under test ------------------------------------//
    // We can easily swap between the DB and the flatfile implementations
    // ---- Please experiment with the different implementations ---- 
    private CategoryEntityFacade categoryEntityFacade = new CategoryEntityFacadeDB();
    private TodoEntityFacade todoEntityFacade = new TodoEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();
    //------------------------------------------------------------------------//

    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreate() {
        long id = categoryEntityFacade.create("Things to buy");
        Assert.assertFalse(id == 0);
    }

    @Test
    public void testFind() {
        long id = categoryEntityFacade.create("Things to buy");
        {
            Category category = categoryEntityFacade.find(id);
            Assert.assertNotNull(category);
            Assert.assertEquals("Things to buy", category.getName());
        }
        {
            Category category = categoryEntityFacade.findByName("Things to buy");
            Assert.assertNotNull(category);
            Assert.assertEquals("Things to buy", category.getName());
        }
    }

    @Test
    public void testAddTodo() {

        long categoryId = categoryEntityFacade.create("Things to buy");
        long todo1Id = todoEntityFacade.create("First", "First Body");
        long todo2Id = todoEntityFacade.create("Second", "Second Body");
        long todo3Id = todoEntityFacade.create("Third", "Third Body");


        {
            categoryEntityFacade.addTodo(categoryId, todo1Id);
            categoryEntityFacade.addTodo(categoryId, todo2Id);

            Category category = categoryEntityFacade.find(categoryId);
            Assert.assertNotNull(category);
            Assert.assertNotNull(category.getTodos());
            Assert.assertEquals(2, category.getTodos().size());
            Assert.assertEquals("First", category.getTodos().get(0).getTitle());
            Assert.assertEquals("Second", category.getTodos().get(1).getTitle());
        }


        {
            categoryEntityFacade.addTodo(categoryId, todo3Id);

            Category category = categoryEntityFacade.find(categoryId);
            Assert.assertNotNull(category);
            Assert.assertNotNull(category.getTodos());
            Assert.assertEquals(3, category.getTodos().size());
            Assert.assertEquals("First", category.getTodos().get(0).getTitle());
            Assert.assertEquals("Second", category.getTodos().get(1).getTitle());
            Assert.assertEquals("Third", category.getTodos().get(2).getTitle());
        }


    }
}