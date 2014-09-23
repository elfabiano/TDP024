package tdp024.todo.data.flatfile.entity;

import java.util.List;
import tdp024.todo.data.api.entity.Category;
import tdp024.todo.data.api.entity.Todo;

public class CategoryFlatfile implements Category {
    
    private long id;
    private String name;
    private List<Todo> todos;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Todo> getTodos() {
        return todos;
    }

    @Override
    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }
    
}
