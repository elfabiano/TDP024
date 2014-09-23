package tdp024.todo.logic.impl.facade;

import tdp024.todo.data.api.entity.Category;
import tdp024.todo.data.api.facade.CategoryEntityFacade;
import tdp024.todo.logic.api.facade.CategoryLogicFacade;

public class CategoryLogicFacadeImpl implements CategoryLogicFacade {

    private CategoryEntityFacade categoryEntityFacade;

    public CategoryLogicFacadeImpl(
            CategoryEntityFacade categoryEntityFacade) {
        this.categoryEntityFacade = categoryEntityFacade;
    }

    @Override
    public void create(String name) {
        categoryEntityFacade.create(name);
    }

    @Override
    public void addTodo(long categoryId, long todoId) {
        categoryEntityFacade.addTodo(categoryId, todoId);
    }

    @Override
    public Category findByName(String name) {
        return categoryEntityFacade.findByName(name);
    }
}
