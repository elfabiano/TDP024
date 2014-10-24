package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.util.exceptions.AccountBalanceException;
import se.liu.ida.tdp024.account.util.exceptions.EntityNotFoundException;
import se.liu.ida.tdp024.account.util.exceptions.InputParameterException;
import se.liu.ida.tdp024.account.util.exceptions.ServiceConfigurationException;


public interface AccountLogicFacade {
    public void create(String accountType, String name, String bank) throws InputParameterException, ServiceConfigurationException;
    
    public List<Account> find(String name) throws InputParameterException, ServiceConfigurationException;
    
    public void debit(long id, int amount) throws 
            InputParameterException, 
            EntityNotFoundException, 
            ServiceConfigurationException, 
            AccountBalanceException ;
    
    public void credit(long id, int amount) throws 
            InputParameterException, 
            EntityNotFoundException, 
            ServiceConfigurationException, 
            AccountBalanceException ;
    
    public List<Transaction> transactions(long id) throws 
            EntityNotFoundException, 
            InputParameterException;
}
