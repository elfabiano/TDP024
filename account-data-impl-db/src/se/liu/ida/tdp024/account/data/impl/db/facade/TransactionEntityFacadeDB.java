/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;

/**
 *
 * @author fabwi272
 */
public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    AccountLogger logger = new AccountLoggerImpl();
    
    @Override
    public long create(String type, int amount, String status) {
        
        EntityManager em = EMF.getEntityManager();
        
        try {
            em.getTransaction().begin();
            
            Transaction transaction  = new TransactionDB();
            
            transaction.setType(type);
            transaction.setAmount(amount);
            transaction.setStatus(status);
            
            em.persist(transaction);
            em.getTransaction().commit();
            
            return transaction.getId();
        }
        catch(Exception e) {
            logger.log(e);
            return 0;
        }
        finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public Transaction find(long id) {
        EntityManager em = EMF.getEntityManager();
        
        try {
            return em.find(TransactionDB.class, id);
            
        } catch (Exception e) {
            logger.log(e);
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Transaction> findAll() {
        EntityManager em = EMF.getEntityManager();
        
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            
            CriteriaQuery cq = cb.createQuery(TransactionDB.class);
            
            Root<Transaction> transaction = cq.from(TransactionDB.class);
            
            cq.select(transaction);
            
            TypedQuery<Transaction> tq = em.createQuery(cq);
            
            List<Transaction> transactions = tq.getResultList();
            return transactions;
        } 
        catch(Exception e) {
            logger.log(e);
            return null;
        }
        finally {
            em.close();
        }
    }

    @Override
    public void update(long id, String type, int amount, String status) {
        EntityManager em = EMF.getEntityManager();
        
        try {
            Transaction transaction = em.find(TransactionDB.class, id);
            
            em.getTransaction().begin();
            transaction.setType(type);
            transaction.setAmount(amount);
            transaction.setStatus(status);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            logger.log(e);
        }
        finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void remove(long id) {
        EntityManager em  = EMF.getEntityManager();
        
        
        try {
            Transaction transaction = em.find(TransactionDB.class, id);
            
            em.getTransaction().begin();
            em.remove(transaction);            
            em.getTransaction().commit();
        }
        catch (Exception e) {
            logger.log(e);
        }
        finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }    
    
}
