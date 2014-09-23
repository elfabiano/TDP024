package tdp024.todo.logic.api.facade;

import tdp024.todo.data.api.entity.Category;

public interface CategoryLogicFacade {

    public void create(String name);

    public void addTodo(long categoryId, long todoId);

    public Category findByName(String name);
}
