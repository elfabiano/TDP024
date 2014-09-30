package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        
        String getPerson = httpHelper.get("http://enterprise-systems.appspot.com/person/find.name" ,"name" ,name);
        Map<String, String> personKey = jsonSerializer.fromJson(getPerson, Map.class);
        
        System.out.println(personKey.get("key"));
        
        String getBank = httpHelper.get("http://enterprise-systems.appspot.com/bank/find.name" ,"name" , bank);
        Map<String, String> bankKey = jsonSerializer.fromJson(getBank, Map.class);
        
        
        accountEntityFacade.create(accountType, personKey.get("key"), bankKey.get("key"));
        
    }

    @Override
    public List<Account> find(String name) {
        HTTPHelper httpHelper = new HTTPHelperImpl();
        
        String getPerson = httpHelper.get("http://enterprise-systems.appspot.com/person/find.name" ,"name" , name);
        Map<String, String> personKey = jsonSerializer.fromJson(getPerson, Map.class);
        
        return accountEntityFacade.findAll(personKey.get("key"));
    }

    @Override
    public void debit(long id, int amount) {
        int holdings = accountEntityFacade.find(id).getHoldings();
        holdings = holdings - amount;
        accountEntityFacade.updateAmount(id, holdings);
        
        
    }

    @Override
    public void credit(long id, int amount) {
        int holdings = accountEntityFacade.find(id).getHoldings();
        holdings = holdings + amount;
        accountEntityFacade.updateAmount(id, holdings);
    }

    @Override
    public List<Transaction> transactions(long id) {
        return accountEntityFacade.find(id).getTransactions();
    }
    
}
