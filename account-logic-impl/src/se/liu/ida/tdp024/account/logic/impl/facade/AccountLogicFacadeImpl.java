package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import java.util.Map;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.api.rules.TransactionVerification;
import se.liu.ida.tdp024.account.logic.impl.constants.Constants;
import se.liu.ida.tdp024.account.logic.impl.rules.BasicTransactionVerification;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    private TransactionVerification transactionVerification = new BasicTransactionVerification();
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    private TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }

    @Override
    public void create(String accountType, String name, String bank) throws Exception {
        
        
        System.out.println(accountType);
        System.out.println(Constants.ACCOUNT_TYPE_CHECK);
        if (accountType.equalsIgnoreCase(Constants.ACCOUNT_TYPE_CHECK) || accountType.equalsIgnoreCase(Constants.ACCOUNT_TYPE_SAVINGS)){
            throw new Exception();
        
        }    
            
        //Call SOA service
        HTTPHelper httpHelper = new HTTPHelperImpl();

        String getPerson = httpHelper.get("http://enterprise-systems.appspot.com/person/find.name" ,"name" ,name);
        Map<String, String> personKey = jsonSerializer.fromJson(getPerson, Map.class);


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
        Account account = accountEntityFacade.find(id);
        String status;
        if(transactionVerification.isAcceptable(Constants.TRANSACTION_TYPE_DEBIT, amount, account)) {
            accountEntityFacade.updateAmount(id, account.getHoldings() - amount);
            status = Constants.TRANSACTION_STATUS_OK;
        }
        else {
            status = Constants.TRANSACTION_STATUS_FAILED;
        }
        
        long transactionId = transactionLogicFacade.create(Constants.TRANSACTION_TYPE_DEBIT, amount, status); 
        accountEntityFacade.addTransaction(id, transactionId);
    }

    @Override
    public void credit(long id, int amount) {
        Account account = accountEntityFacade.find(id);
        String status;
        System.out.println("credit");
        if(transactionVerification.isAcceptable(Constants.TRANSACTION_TYPE_CREDIT, amount, account)) {
            accountEntityFacade.updateAmount(id, account.getHoldings() + amount);
            status = Constants.TRANSACTION_STATUS_OK;
        }
        else {
            status = Constants.TRANSACTION_STATUS_FAILED;
        }
        
        long transactionId = transactionLogicFacade.create(Constants.TRANSACTION_TYPE_CREDIT, amount, status); 
        accountEntityFacade.addTransaction(id, transactionId);
    }

    @Override
    public List<Transaction> transactions(long id) {
        return accountEntityFacade.find(id).getTransactions();
    }
    
}
