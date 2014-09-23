package tdp024.todo.data.db.facade;

import java.util.List;
import java.util.ServiceConfigurationError;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import tdp024.todo.data.api.entity.Todo;
import tdp024.todo.data.db.entity.TodoDB;
import tdp024.todo.data.api.facade.TodoEntityFacade;
import tdp024.todo.data.db.util.EMF;
import tdp024.util.logger.TodoLogger;
import tdp024.util.logger.TodoLoggerImpl;

public class TodoEntityFacadeDB implements TodoEntityFacade {

    private static final TodoLogger todoLogger = new TodoLoggerImpl();

    @Override
    public long create(String title, String body) {

        EntityManager em = EMF.getEntityManager();

        try {

            em.getTransaction().begin();

            Todo todo = new TodoDB();
            todo.setTitle(title);
            todo.setBody(body);

            em.persist(todo);

            em.getTransaction().commit();

            return todo.getId();

        } catch (Exception e) {

            todoLogger.log(e);
            throw new ServiceConfigurationError("Commit fails");

        } finally {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }

    }

    @Override
    public void setActive(long id, boolean active) {

        EntityManager em = EMF.getEntityManager();

        try {

            em.getTransaction().begin();

            Todo todo = em.find(TodoDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            todo.setActive(active);

            em.merge(todo);

            em.getTransaction().commit();

        } catch (Exception e) {

            todoLogger.log(e);

        } finally {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.close();
        }

    }

    @Override
    public Todo find(long id) {

        EntityManager em = EMF.getEntityManager();

        try {

            return em.find(TodoDB.class, id);

        } catch (Exception e) {

            todoLogger.log(e);
            return null;

        } finally {
            em.close();
        }

    }

    @Override
    public List<Todo> findAll() {

        EntityManager em = EMF.getEntityManager();

        try {

            Query query = em.createQuery("SELECT t FROM TodoDB t");
            return query.getResultList();

        } catch (Exception e) {

            todoLogger.log(e);
            return null;

        } finally {
            em.close();
        }

    }
}
