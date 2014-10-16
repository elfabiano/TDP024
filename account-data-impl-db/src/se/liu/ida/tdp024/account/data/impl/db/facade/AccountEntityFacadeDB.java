package se.liu.ida.tdp024.account.data.impl.db.facade;

import static java.lang.StrictMath.abs;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.Constants;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.util.logger.AccountLogger;
import se.liu.ida.tdp024.account.util.logger.AccountLoggerImpl;

public class AccountEntityFacadeDB implements AccountEntityFacade {
    
    private static final AccountLogger accountLogger = new AccountLoggerImpl();
    private final TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    
    @Override
    public long create(String accountType, String personKey, String bankKey) {
        EntityManager em = EMF.getEntityManager();
        
        try {
            em.getTransaction().begin();
            Account account = new AccountDB();
            
            account.setAccountType(accountType);
            account.setPersonKey(personKey);
            account.setBankKey(bankKey);
            account.setHoldings(0);
            
            em.persist(account);
            em.getTransaction().commit();
            
            return account.getId();            
        }
        catch(Exception e){
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
    public void updateAmount(long id, int change) throws Exception {
        EntityManager em = EMF.getEntityManager();
        try {            
            em.getTransaction().begin();
            Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            String status;
            String transactionType;
        
            if(change < 0) { 
                transactionType = Constants.TRANSACTION_TYPE_DEBIT;
                if(account.getHoldings() >= -change) {             
            
                    account.setHoldings(account.getHoldings() + change);
                    status = Constants.TRANSACTION_STATUS_OK;
                } 
                else {                    
                    status = Constants.TRANSACTION_STATUS_FAILED;
                    
                }
            }
            else {
                account.setHoldings(account.getHoldings() + change);
                status = Constants.TRANSACTION_STATUS_OK;
                transactionType = Constants.TRANSACTION_TYPE_CREDIT;
            }
        
            long transactionId = transactionEntityFacade.create(transactionType, abs(change), status); 
            Transaction transaction = em.find(TransactionDB.class, transactionId);
            transaction.setAccount(account);
            em.merge(account);
            em.merge(transaction);
            em.getTransaction().commit();
            
            if (status.equals((Constants.TRANSACTION_STATUS_FAILED))){
                em.close();
                throw new Exception("transaction failed");
            }
            
        } catch(Exception e){
            accountLogger.log(e);
            throw e;
        }
        finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void addTransaction(long accountId, long transactionId) throws Exception {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            
            Account account = em.find(AccountDB.class, accountId);
            
            Transaction transaction = em.find(TransactionDB.class, transactionId);
            transaction.setAccount(account);
            em.merge(transaction);
            
            em.getTransaction().commit();
        }
        catch(Exception e){
            accountLogger.log(e);
            throw e;
        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void remove(long id) throws Exception {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            
            Account account = em.find(AccountDB.class, id);
            em.remove(account);
            em.getTransaction().commit();
        } catch(Exception e){
            accountLogger.log(e);
            throw e;
        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }



}
