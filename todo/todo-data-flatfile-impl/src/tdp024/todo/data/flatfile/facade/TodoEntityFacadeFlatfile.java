package tdp024.todo.data.flatfile.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import tdp024.todo.data.api.entity.Todo;
import tdp024.todo.data.api.facade.TodoEntityFacade;
import tdp024.todo.data.flatfile.entity.TodoFlatfile;
import tdp024.util.logger.TodoLogger;
import tdp024.util.logger.TodoLoggerImpl;

public class TodoEntityFacadeFlatfile implements TodoEntityFacade {

    private static final TodoLogger todoLogger = new TodoLoggerImpl();
    private static final String DIR_PATH_TODO = "flatfilestore" + File.separator + "todo" + File.separator;

    static {
        try {
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
    public long create(String title, String body) {

        try {

            long id = Math.abs(new Random().nextLong());
            File file = new File(DIR_PATH_TODO + id + ".todo");
            while (file.exists()) {
                id = new Random().nextLong();
                file = new File(DIR_PATH_TODO + id + ".todo");
            }

            Todo todo = new TodoFlatfile();
            todo.setTitle(title);
            todo.setBody(body);
            todo.setId(id);

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(todo);
            oos.close();
            fos.close();

            return id;

        } catch (Exception e) {
            todoLogger.log(e);
            return 0;
        }

    }

    @Override
    public void setActive(long id, boolean active) {
        try {

            Todo todo = find(id);
            if (todo == null) {
                return;
            }

            todo.setActive(active);

            File file = new File(DIR_PATH_TODO + todo.getId() + ".todo");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(todo);
            oos.close();
            fos.close();

        } catch (Exception e) {
            todoLogger.log(e);
        }
    }

    @Override
    public Todo find(long id) {

        try {

            File file = new File(DIR_PATH_TODO + id + ".todo");
            if (!file.exists()) {
                return null;
            }

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Todo todo = (Todo) ois.readObject();
            ois.close();
            fis.close();

            return todo;

        } catch (Exception e) {
            todoLogger.log(e);
            return null;
        }
    }

    @Override
    public List<Todo> findAll() {

        List<Todo> todos = new ArrayList<Todo>();

        try {

            File directory = new File(DIR_PATH_TODO);
            for (File file : directory.listFiles()) {
                //Not pretty!
                long id = -1;
                if (File.separator.equals("/")) {
                    id = Long.parseLong(file.getPath().split("/")[2].split("\\.")[0]);
                } else if (File.separator.equals("\\")) {
                    id = Long.parseLong(file.getPath().split("\\\\")[2].split("\\.")[0]);
                }
                todos.add(find(id));
            }

        } catch (Exception e) {
            todoLogger.log(e);
        }

        return todos;

    }
}
