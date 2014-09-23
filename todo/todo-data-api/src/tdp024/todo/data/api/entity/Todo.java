package tdp024.todo.data.api.entity;

import java.io.Serializable;

public interface Todo extends Serializable {

    public long getId();

    public void setId(long id);

    public String getTitle();

    public void setTitle(String title);

    public String getBody();

    public void setBody(String body);

    public boolean isActive();

    public void setActive(boolean active);

    public Category getCategory();

    public void setCategory(Category category);
}
