package tdp024.todo.data.api.facade;

import tdp024.todo.data.api.entity.Category;

public interface CategoryEntityFacade {

    public long create(String name);

    public void addTodo(long categoryId, long todoId);

    public Category find(long id);

    public Category findByName(String name);
}
