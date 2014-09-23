package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    public long create(String accountType, String personKey, String bankKey);
    
    public void updateAmount(long id, int newAmount);
    
    public Account find(long id);
    
    public List<Account> findAll();
    
    public List<Account> findAll(String personKey);
    
    public void remove(long id);
    
}
