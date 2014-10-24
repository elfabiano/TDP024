package se.liu.ida.tdp024.account.data.impl.db.facade;

import se.liu.ida.tdp024.account.util.exceptions.AccountBalanceException;
import se.liu.ida.tdp024.account.util.exceptions.ServiceConfigurationException;
import se.liu.ida.tdp024.account.util.exceptions.InputParameterException;
import static java.lang.StrictMath.abs;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.*;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.util.exceptions.EntityNotFoundException;
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
    public long create(String accountType, String personKey, String bankKey) throws 
            InputParameterException, 
            ServiceConfigurationException{
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
        catch(IllegalArgumentException e){
            accountLogger.log(e);
            throw new InputParameterException("Wrong parameters.");}
        catch(RollbackException e){
            accountLogger.log(e);
            throw new ServiceConfigurationException("Server Error.");
        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }            
            em.close();
        }
    }

    @Override
    public Account find(long id) throws EntityNotFoundException, InputParameterException{
        EntityManager em = EMF.getEntityManager();
        try {   
            Account result = em.find(AccountDB.class, id);
            if (result == null){
                throw new EntityNotFoundException("Account does not exist.");
            }
            return result;
        } 
        catch(IllegalArgumentException e){
            accountLogger.log(e);
            throw new InputParameterException("Wrong Parameters.");
        } finally {
            em.close();
        }
    }

    @Override
    public List<Account> findAll() throws ServiceConfigurationException{
        EntityManager em = EMF.getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(AccountDB.class);
            
            Root<Account> account = cq.from(AccountDB.class);
            cq.select(account.get("id"));
            
            TypedQuery<Account> q = em.createQuery(cq);
            List<Account> results = q.getResultList();
            
            return results;
            
        } catch(IllegalArgumentException e){
            accountLogger.log(e);
            throw new ServiceConfigurationException("Server Error.");
        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public List<Account> findAll(String personKey) throws ServiceConfigurationException{
        EntityManager em = EMF.getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(AccountDB.class);
            
            Root<Account> account = cq.from(AccountDB.class);
            cq.where(account.get("personKey").in(personKey));
            
            TypedQuery<Account> q = em.createQuery(cq);
            List<Account> results = q.getResultList();
                        
            return results;
        } catch(IllegalArgumentException e){
            accountLogger.log(e);
            throw new ServiceConfigurationException("Server Error."); 
        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void updateAmount(long id, int change) throws 
            InputParameterException, 
            EntityNotFoundException,
            ServiceConfigurationException,
            AccountBalanceException{
        EntityManager em = EMF.getEntityManager();
        try {            
            em.getTransaction().begin();
            Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (account == null){
                throw new EntityNotFoundException("Account does not exist.");
            }
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
            if (transaction == null){
                //Should not be thrown
                throw new EntityNotFoundException("Transaction does not exist.");
            }
            transaction.setAccount(account);
            em.merge(account);
            em.merge(transaction);
            em.getTransaction().commit();
            
            if (status.equals((Constants.TRANSACTION_STATUS_FAILED))){
                throw new AccountBalanceException();
            }
            
        } 
        catch(IllegalArgumentException e){
            accountLogger.log(e);
            throw new InputParameterException("Wrong parameters.");
        } catch(PessimisticLockException e){
            accountLogger.log(e);
            throw new ServiceConfigurationException("Server Error.");
        }
        catch(LockTimeoutException e){
            accountLogger.log(e);
            throw new ServiceConfigurationException("Server Error.");
        } catch(PersistenceException e){
            accountLogger.log(e);
            throw new ServiceConfigurationException("Server Error.");
        } 
        finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void addTransaction(long accountId, long transactionId) throws 
            EntityNotFoundException, 
            InputParameterException,
            ServiceConfigurationException{
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            
            Account account = em.find(AccountDB.class, accountId);
            if (account == null){
                throw new EntityNotFoundException();
            }
            
            Transaction transaction = em.find(TransactionDB.class, transactionId);
            if (transaction == null){
                throw new EntityNotFoundException();
            }
            transaction.setAccount(account);
            em.merge(transaction);
            
            em.getTransaction().commit();
        }
        catch(IllegalArgumentException e){
            accountLogger.log(e);
            throw new InputParameterException("Wrong parameters.");
        } 
        catch(RollbackException e){
            accountLogger.log(e);
            throw new ServiceConfigurationException("Server Error.");
        }finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public void remove(long id) throws 
            EntityNotFoundException, 
            InputParameterException, 
            ServiceConfigurationException  {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            
            Account account = em.find(AccountDB.class, id);
            if (account == null){
                throw new EntityNotFoundException();
            }
            
            em.remove(account);
            em.getTransaction().commit();
        } catch(IllegalArgumentException e){
            accountLogger.log(e);
            throw new InputParameterException("Wrong parameters.");
        } 
        catch(RollbackException e){
            accountLogger.log(e);
            throw new ServiceConfigurationException("Server Error.");
        }finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }



}
