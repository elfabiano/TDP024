package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.exceptions.InputParameterException;

public class AccountLogicFacadeTest {

    
    //--- Unit under test ---//
    private AccountEntityFacade accountEntityFacade;
    private AccountLogicFacade accountLogicFacade;
    private StorageFacade storageFacade = new StorageFacadeDB();
    
    @Before
    public void setup(){
        accountEntityFacade = new AccountEntityFacadeDB();
        accountLogicFacade = new AccountLogicFacadeImpl(accountEntityFacade);
        storageFacade = new StorageFacadeDB();
    }
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    
    
    @Test
    public void testCreate() throws Exception {
        String accType = "Savings";
        String name = "Lisa Lisasson";
        String bank = "SWEDBANK";
        accountLogicFacade.create(accType,name, bank);
        
        Account account = accountEntityFacade.find(1);
        Assert.assertTrue(  account != null &&
                            account.getBankKey() != null &&
                            account.getHoldings() == 0 &&
                            account.getPersonKey() != null &&
                            account.getAccountType().equals(accType));        
    }
    
    @Test
    public void testCreateFail() throws Exception {
        String accType = "Savings";
        String name = "Lisa Lis";
        String bank = "SWEDBANK";
        
        try {
            accountLogicFacade.create(accType,name, bank);
        } catch (InputParameterException e) {
            Assert.assertTrue(true);
        }
        
        accType = "Ssdm";
        name = "Lisa Lisasson";
        bank = "SWEDBANK";
        
        try {
            accountLogicFacade.create(accType,name, bank);
        } catch (InputParameterException e) {
            Assert.assertTrue(true);
        }
             
    }
    
    @Test
    public void testFind() throws Exception {
        String accType = "CHECK";
        String name = "Lisa Lisasson";
        String bank = "SWEDBANK";
        accountLogicFacade.create(accType,name, bank);
        List<Account> results = accountLogicFacade.find(name);
        Account account = accountEntityFacade.find(1);
        Assert.assertFalse(results.isEmpty());
        Assert.assertTrue(results.get(0).getId() == account.getId());
    
    }
    
        @Test
    public void testFindFail() throws Exception {
        String accType = "CHECK";
        String name = "Lisa Lisasson";
        String bank = "SWEDBANK";
        accountLogicFacade.create(accType,name, bank);
        try {
            List<Account> results = accountLogicFacade.find("Lisa");    
        } catch (InputParameterException e) {
            Assert.assertTrue(true);
        }
    }
    
    @Test
    public void testCredit() throws Exception {
        String accType = "Savings";
        String name = "Lisa Lisasson";
        String bank = "SWEDBANK";

        accountLogicFacade.create(accType,name, bank);

        accountLogicFacade.credit(1, 10);
        Account account = accountEntityFacade.find(1);
        List<Transaction> transactions = account.getTransactions();
        
        Assert.assertTrue(account.getHoldings() == 10 &&
                            transactions != null);
        
    }
    
    @Test
    public void testDebit() throws Exception {
        String accType = "Check";
        String name = "Lisa Lisasson";
        String bank = "SWEDBANK";
        accountLogicFacade.create(accType,name, bank);
        accountLogicFacade.credit(1, 10);
        accountLogicFacade.debit(1, 5);
        Account account = accountEntityFacade.find(1);
        List<Transaction> transactions = account.getTransactions();
        
        Assert.assertTrue(account.getHoldings() == 5 &&
                            transactions != null);
        
    }
    
    @Test
    public void testTransactions() throws Exception {
        storageFacade.emptyStorage();
        
        String accType = "CHECK";
        String name = "Lisa Lisasson";
        String bank = "SWEDBANK";
        accountLogicFacade.create(accType, name, bank);

        accountLogicFacade.credit(1, 10);
        accountLogicFacade.debit(1, 5);
        List<Transaction> transactions = accountLogicFacade.transactions(1);
        
        Assert.assertTrue(transactions.size() == 2);
    }
    
}