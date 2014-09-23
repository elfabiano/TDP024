package tdp024.todo.data.flatfile.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import tdp024.todo.data.api.entity.Category;
import tdp024.todo.data.api.entity.Todo;
import tdp024.todo.data.api.facade.CategoryEntityFacade;
import tdp024.todo.data.api.facade.TodoEntityFacade;
import tdp024.todo.data.flatfile.entity.CategoryFlatfile;
import tdp024.util.logger.TodoLogger;
import tdp024.util.logger.TodoLoggerImpl;

public class CategoryEntityFacadeFlatfile implements CategoryEntityFacade {

    private static final TodoLogger todoLogger = new TodoLoggerImpl();
    private static final String DIR_PATH_CATEGORY = "flatfilestore" + File.separator + "category" + File.separator;
    private static final String DIR_PATH_TODO = "flatfilestore" + File.separator + "todo" + File.separator;
    //
    private final TodoEntityFacade todoEntityFacade = new TodoEntityFacadeFlatfile();

    static {
        try {

            {
                File file = new File(DIR_PATH_CATEGORY);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            {
                File file = new File(DIR_PATH_TODO);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }

        } catch (Exception e) {
            todoLogger.log(e);
        }
    }

    @Override
    public long create(String name) {
        try {

            long id = Math.abs(new Random().nextLong());
            File file = new File(DIR_PATH_CATEGORY + id + ".category");
            while (file.exists()) {
                id = new Random().nextLong();
                file = new File(DIR_PATH_CATEGORY + id + ".category");
            }

            Category category = new CategoryFlatfile();
            category.setId(id);
            category.setName(name);

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(category);
            oos.close();
            fos.close();

            return id;

        } catch (Exception e) {
            todoLogger.log(e);
            return 0;
        }
    }

    @Override
    public void addTodo(long categoryId, long todoId) {
        try {


            Todo todo = todoEntityFacade.find(todoId);
            if (todo == null) {
                return;
            }

            Category category = find(categoryId);

            todo.setCategory(category);
            if (category.getTodos() == null) {
                category.setTodos(new ArrayList<Todo>());
            }
            category.getTodos().add(todo);


            {
                File file = new File(DIR_PATH_TODO + todo.getId() + ".todo");
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(todo);
                oos.close();
                fos.close();
            }
            {
                File file = new File(DIR_PATH_CATEGORY + category.getId() + ".category");
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(category);
                oos.close();
                fos.close();
            }

        } catch (Exception e) {
            todoLogger.log(e);
        }
    }

    @Override
    public Category find(long id) {
        try {

            File file = new File(DIR_PATH_CATEGORY + id + ".category");
            if (!file.exists()) {
                return null;
            }

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Category category = (Category) ois.readObject();
            ois.close();
            fis.close();

            return category;

        } catch (Exception e) {
            todoLogger.log(e);
            return null;
        }
    }

    @Override
    public Category findByName(String name) {

        try {

            File directory = new File(DIR_PATH_CATEGORY);
            for (File file : directory.listFiles()) {
                //Not pretty!
                long id = -1;
                if (File.separator.equals("/")) {
                    id = Long.parseLong(file.getPath().split("/")[2].split("\\.")[0]);
                } else if (File.separator.equals("\\")) {
                    id = Long.parseLong(file.getPath().split("\\\\")[2].split("\\.")[0]);
                }
                Category category = find(id);
                if (category.getName().equals(name)) {
                    return category;
                }
            }

            return null;

        } catch (Exception e) {
            todoLogger.log(e);
            return null;
        }
    }
}
