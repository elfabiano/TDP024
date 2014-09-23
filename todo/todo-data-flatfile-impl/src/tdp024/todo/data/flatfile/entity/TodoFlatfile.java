package tdp024.todo.data.flatfile.entity;

import tdp024.todo.data.api.entity.Category;
import tdp024.todo.data.api.entity.Todo;

public class TodoFlatfile implements Todo {

    private long id;
    private String title;
    private String body;
    private boolean active;
    private Category category;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id  = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }
}
