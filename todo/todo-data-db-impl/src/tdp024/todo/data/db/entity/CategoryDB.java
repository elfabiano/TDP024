package tdp024.todo.data.db.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import tdp024.todo.data.api.entity.Category;
import tdp024.todo.data.api.entity.Todo;

@Entity
public class CategoryDB implements Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String name;
    
    @OneToMany(mappedBy = "category", targetEntity = TodoDB.class)
    private List<Todo> todos;
    
    
    //--- Getters and Setters ---//
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
