package tdp024.todo.data.db.facade;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import tdp024.todo.data.api.entity.Category;
import tdp024.todo.data.api.entity.Todo;
import tdp024.todo.data.db.entity.CategoryDB;
import tdp024.todo.data.db.entity.TodoDB;
import tdp024.todo.data.api.facade.CategoryEntityFacade;
import tdp024.todo.data.db.util.EMF;
import tdp024.util.logger.TodoLogger;
import tdp024.util.logger.TodoLoggerImpl;

public class CategoryEntityFacadeDB implements CategoryEntityFacade {

    private static final TodoLogger todoLogger = new TodoLoggerImpl();

    @Override
    public long create(String name) {

        EntityManager em = EMF.getEntityManager();

        try {

            em.getTransaction().begin();

            Category category = new CategoryDB();
            category.setName(name);

            em.persist(category);

            em.getTransaction().commit();

            return category.getId();

        } catch (Exception e) {

            todoLogger.log(e);
            return 0;

        } finally {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.close();

        }

    }

    @Override
    public void addTodo(long categoryId, long todoId) {

        EntityManager em = EMF.getEntityManager();

        try {

            em.getTransaction().begin();

            Category category = em.find(CategoryDB.class, categoryId);

            Todo todo = em.find(TodoDB.class, todoId);
            todo.setCategory(category);

            em.merge(todo);

            em.getTransaction().commit();

        } catch (Exception e) {

            todoLogger.log(e);
            return;

        } finally {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.close();

        }

    }

    @Override
    public Category find(long id) {

        EntityManager em = EMF.getEntityManager();

        try {

            return em.find(CategoryDB.class, id);

        } catch (Exception e) {

            todoLogger.log(e);
            return null;

        } finally {

            em.close();

        }
    }

    @Override
    public Category findByName(String name) {
        EntityManager em = EMF.getEntityManager();

        try {

            Query query = em.createQuery("SELECT c FROM CategoryDB c WHERE c.name = :name ");
            query.setParameter("name", name);

            return (Category) query.getSingleResult();

        } catch (Exception e) {

            todoLogger.log(e);
            return null;

        } finally {

            em.close();

        }
    }
}
