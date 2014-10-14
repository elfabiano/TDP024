package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;


public interface AccountLogicFacade {
    public void create(String accountType, String name, String bank) throws Exception;
    
    public List<Account> find(String name) throws Exception;
    
    public void debit(long id, int amount) throws Exception;
    
    public void credit(long id, int amount) throws Exception;
    
    public List<Transaction> transactions(long id);
}
