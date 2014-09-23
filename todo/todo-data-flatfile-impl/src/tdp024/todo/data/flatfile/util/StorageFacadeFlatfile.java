package tdp024.todo.data.flatfile.util;

import java.io.File;
import tdp024.todo.data.api.util.StorageFacade;
import tdp024.util.logger.TodoLogger;
import tdp024.util.logger.TodoLoggerImpl;

public class StorageFacadeFlatfile implements StorageFacade {

    private static final String DIR_PATH_CATEGORY = "flatfilestore/category/";
    private static final String DIR_PATH_TODO = "flatfilestore/todo/";
    private static final TodoLogger todoLogger = new TodoLoggerImpl();

    @Override
    public void emptyStorage() {
        emptyFolder(DIR_PATH_CATEGORY);
        emptyFolder(DIR_PATH_TODO);
    }

    private void emptyFolder(String folderName) {

        try {

            File folder = new File(folderName);
            if (folder.exists()) {
                for(File file : folder.listFiles()) {
                    file.delete();
                }
            }

        } catch (Exception e) {
            todoLogger.log(e);
        }

    }
}
