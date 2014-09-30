package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;

public class AccountEntityFacadeDB implements AccountEntityFacade {
    
    private static final AccountLogger accountLogger = new AccountLoggerImpl();
    
    @Override
    public long create(String accountType, String personKey, String bankKey) {
        EntityManager em = EMF.getEntityManager();
        
        try {
            em.getTransaction().begin();
            Account account = new AccountDB();
            
            account.setAccountType(accountType);
            account.setBankKey(bankKey);
            account.setHoldings(0);
            
            em.persist(account);
            em.getTransaction().commit();
            
            return account.getId();
            
        }
        catch(Exception e){
            System.out.println(e);
            accountLogger.log(e);
            return 0;
        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            
            em.close();
        }
        
    }

    @Override
    public Account find(long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.find(AccountDB.class, id);
        } catch(Exception e){
            accountLogger.log(e);
            return null; 
        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
           
    }

    @Override
    public List<Account> findAll() {
        EntityManager em = EMF.getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(AccountDB.class);
            
            Root<Account> account = cq.from(AccountDB.class);
            cq.select(account.get("id"));
            
            TypedQuery<Account> q = em.createQuery(cq);
            List<Account> results = q.getResultList();
            
            return results;
            
        } catch(Exception e){
            accountLogger.log(e);
            return null; 
        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public List<Account> findAll(String personKey) {
        EntityManager em = EMF.getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(AccountDB.class);
            
            Root<Account> account = cq.from(AccountDB.class);
            cq.where(account.get("personKey").in(personKey));
            
            TypedQuery<Account> q = em.createQuery(cq);
            List<Account> results = q.getResultList();
            
            return results;
        } catch(Exception e){
            accountLogger.log(e);
            return null; 
        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void updateAmount(long id, int newAmount) {
        EntityManager em = EMF.getEntityManager();
        try {
            em.find(AccountDB.class, id).setHoldings(newAmount);
            System.out.println(newAmount);
        } catch(Exception e){
            accountLogger.log(e);
        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }



}
