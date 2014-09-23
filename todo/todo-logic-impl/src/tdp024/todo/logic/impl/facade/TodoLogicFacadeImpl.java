package tdp024.todo.logic.impl.facade;

import tdp024.todo.data.api.entity.Todo;
import tdp024.todo.data.api.facade.TodoEntityFacade;
import tdp024.todo.logic.api.facade.TodoLogicFacade;

public class TodoLogicFacadeImpl implements TodoLogicFacade {

    private TodoEntityFacade todoEntityFacade;

    public TodoLogicFacadeImpl(TodoEntityFacade todoEntityFacade) {
        this.todoEntityFacade = todoEntityFacade;
    }

    @Override
    public long create(String title, String body) {
        return todoEntityFacade.create(title, body);
    }

    @Override
    public Todo find(long id) {
        return todoEntityFacade.find(id);
    }

    @Override
    public void checkOut(long id) {
        this.todoEntityFacade.setActive(id, true);
    }

    @Override
    public void checkIn(long id) {
        this.todoEntityFacade.setActive(id, false);
    }
}
