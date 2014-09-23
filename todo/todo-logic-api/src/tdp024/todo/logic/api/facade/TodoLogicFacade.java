package tdp024.todo.logic.api.facade;

import tdp024.todo.data.api.entity.Todo;

public interface TodoLogicFacade {

    public long create(String title, String body);

    public Todo find(long id);

    public void checkOut(long id);

    public void checkIn(long id);
}
