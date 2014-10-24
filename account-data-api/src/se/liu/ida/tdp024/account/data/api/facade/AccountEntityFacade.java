package se.liu.ida.tdp024.account.data.api.facade;


import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.util.exceptions.AccountBalanceException;
import se.liu.ida.tdp024.account.util.exceptions.EntityNotFoundException;
import se.liu.ida.tdp024.account.util.exceptions.InputParameterException;
import se.liu.ida.tdp024.account.util.exceptions.ServiceConfigurationException;

public interface AccountEntityFacade {
    public long create(String accountType, String personKey, String bankKey) throws 
            InputParameterException,
            ServiceConfigurationException;
    
    public void updateAmount(long id, int change) throws 
            InputParameterException, 
            EntityNotFoundException,
            ServiceConfigurationException,
            AccountBalanceException;
    
    public Account find(long id) throws EntityNotFoundException, InputParameterException;
    
    public List<Account> findAll() throws ServiceConfigurationException;
    
    public List<Account> findAll(String personKey) throws ServiceConfigurationException;
    
    public void addTransaction(long accountId, long transactionId) throws 
            EntityNotFoundException, 
            InputParameterException,
            ServiceConfigurationException;

    public void remove(long id) throws 
            EntityNotFoundException, 
            InputParameterException,
            ServiceConfigurationException;
    
}
