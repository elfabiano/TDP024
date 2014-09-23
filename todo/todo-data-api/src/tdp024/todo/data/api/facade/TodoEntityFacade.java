package tdp024.todo.data.api.facade;

import java.util.List;
import tdp024.todo.data.api.entity.Todo;

public interface TodoEntityFacade {

    public long create(String title, String body);

    public void setActive(long id, boolean active);

    public Todo find(long id);

    public List<Todo> findAll();
}
