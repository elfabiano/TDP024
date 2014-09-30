package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }

    @Override
    public void create(String accountType, String name, String bank) {
        
        //Call SOA service
        HTTPHelper httpHelper = new HTTPHelperImpl();
        
        String getResult = httpHelper.get("http://enterprise-systems.appspot.com/person/find.name" ,"name" ,"Lisa Lisasson");
        
        
        System.out.println(getResult);
        System.out.println("hej");
        //accountEntityFacade.create(accountType, "", "");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Account> find(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void debit(long id, int amount) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void credit(long id, int amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Transaction> transactions(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
