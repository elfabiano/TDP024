package tdp024.logic.facade.impl.test;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import tdp024.todo.data.api.entity.Todo;
import tdp024.todo.data.api.facade.TodoEntityFacade;
import tdp024.todo.data.api.util.StorageFacade;
import tdp024.todo.data.db.facade.TodoEntityFacadeDB;
import tdp024.todo.data.db.util.StorageFacadeDB;
import tdp024.todo.logic.api.facade.TodoLogicFacade;
import tdp024.todo.logic.impl.facade.TodoLogicFacadeImpl;

public class TodoLogicFacadeImplTest {

    //------------------ Mock ------------------------------------------------//
    /*
     * It might be the case that we do not have any implementation of 
     * the TodoEntityFacade available (no one has implemented it yet),
     * so do we have to wait for this before we can write our tests?
     * 
     * No, we can create a mock by implementing this interface ourselfs.
     * 
     * Here is an example of a mock that does not accomplish much.
     */
    private TodoEntityFacade mock = new TodoEntityFacade() {
        @Override
        public long create(String title, String body) {
            return 0;
        }

        @Override
        public void setActive(long id, boolean active) {
            return;
        }

        @Override
        public Todo find(long id) {
            return null;
        }

        @Override
        public List<Todo> findAll() {
            return null;
        }
    };
    //------------------------------------------------------------------------//
    //------------------ Units under test ------------------------------------//
    // Not only can we easily swap between implementations of the logic facade,
    // but note how we are "injecting" the DB implementation of the entity facade
    // into the logic facade.
    //
    // Here it would be possible to inject the mock object we just created.
    //
    // ---- Please experiment with the different implementations ---- 
    //
    private TodoLogicFacade todoLogicFacade = new TodoLogicFacadeImpl(new TodoEntityFacadeDB());
    private StorageFacade storageFacade = new StorageFacadeDB();
    //------------------------------------------------------------------------//
    
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }

    @Test
    public void testNotFound() {

        Assert.assertNull(todoLogicFacade.find(5));
        Assert.assertNull(todoLogicFacade.find(0));
        Assert.assertNull(todoLogicFacade.find(1));
        Assert.assertNull(todoLogicFacade.find(100));
        Assert.assertNull(todoLogicFacade.find(1001));


    }

    @Test
    public void testCheckInCheckOut()  {

        long id = todoLogicFacade.create("Title", "Body");

        {
            todoLogicFacade.checkOut(id);
            Todo todo = todoLogicFacade.find(id);
            Assert.assertTrue(todo.isActive());
        }
        {
            todoLogicFacade.checkIn(id);
            Todo todo = todoLogicFacade.find(id);
            Assert.assertFalse(todo.isActive());
        }


    }
}