package tdp024.todo.data.api.entity;

import java.io.Serializable;
import java.util.List;

public interface Category extends Serializable {

    public long getId();

    public void setId(long id);

    public String getName();

    public void setName(String name);

    public List<Todo> getTodos();

    public void setTodos(List<Todo> todos);
}
