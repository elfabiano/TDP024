package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.Constants;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();;
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
        String accType = "CHECK";
        String personKey = "asgahfshs82";
        String bankKey = "blablablabla";
        
        long id = accountEntityFacade.create(accType, personKey, bankKey);
        System.out.println(id);
        Assert.assertFalse(id==0);
    }
    
    @Test
    public void findTest() {
        String accType = "CHECK";
        String personKey = "asgahfshs82";
        String bankKey = "blablablabla";
        
        long id = accountEntityFacade.create(accType, personKey, bankKey);       
        Assert.assertTrue(accountEntityFacade.find(id) != null);
    }
    
    @Test
    public void findFailTest() {
        String accType = "CHECK";
        String personKey = "asgahfshs82";
        String bankKey = "blablablabla";
        
        long id = accountEntityFacade.create(accType, personKey, bankKey);       
        Assert.assertTrue(accountEntityFacade.find(id + 1) == null);
    }
    
    @Test
    public void findAllTest() {
        storageFacade.emptyStorage();

        accountEntityFacade.create("CHECK", "badgadgadgadg", "agasgasgagasg");
        accountEntityFacade.create("CHECK", "asfasfafadsfas", "sdfsdfsadfsfs");
        accountEntityFacade.create("CHECK", "fbdfhdtjrths", "fadgsfbdfbdferghs");
        accountEntityFacade.create("CHECK", "gsfbcgdgsdgs", "sdfsdfaweref");
        accountEntityFacade.create("CHECK", "fhdfndfhsdfadf", "vadgfdhghgadg");
        
        List<Account> accounts = accountEntityFacade.findAll();
        Assert.assertTrue(accounts.size() == 5);
    }
    
    @Test
    public void findAllTestKey() {
        storageFacade.emptyStorage();

        accountEntityFacade.create("CHECK", "badgadgadgadg", "agasgasgagasg");
        accountEntityFacade.create("CHECK", "asfasfafadsfas", "sdfsdfsadfsfs");
        accountEntityFacade.create("CHECK", "badgadgadgadg", "fadgsfbdfbdferghs");
        accountEntityFacade.create("CHECK", "gsfbcgdgsdgs", "sdfsdfaweref");
        accountEntityFacade.create("CHECK", "fhdfndfhsdfadf", "vadgfdhghgadg");
        
        List<Account> accounts = accountEntityFacade.findAll("badgadgadgadg");
        
        Assert.assertTrue(accounts.size() == 2);
    }
    
    @Test
    public void findAllTestKeyFail() {

        accountEntityFacade.create("CHECK", "badgadgadgadg", "agasgasgagasg");
        accountEntityFacade.create("CHECK", "asfasfafadsfas", "sdfsdfsadfsfs");
        accountEntityFacade.create("CHECK", "badgadgadgadg", "fadgsfbdfbdferghs");
        accountEntityFacade.create("CHECK", "gsfbcgdgsdgs", "sdfsdfaweref");
        accountEntityFacade.create("CHECK", "fhdfndfhsdfadf", "vadgfdhghgadg");
        
        List<Account> accounts = accountEntityFacade.findAll("cccss");
        
        Assert.assertTrue(accounts.size() == 0);
    }
    
    @Test
    public void updateAmountTest() throws Exception {
        storageFacade.emptyStorage();

        accountEntityFacade.create("CHECK", "badgadgadgadg", "agasgasgagasg");
        accountEntityFacade.updateAmount(1, 30);
        Assert.assertTrue(accountEntityFacade.find(1).getHoldings() == 30);
        
        accountEntityFacade.updateAmount(1, -30);
        
        Assert.assertTrue(accountEntityFacade.find(1).getHoldings() == 0);
        
        try {
            accountEntityFacade.updateAmount(1, -1);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
        
        try {
            accountEntityFacade.updateAmount(2, 30);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }
    
    @Test
    public void addTransactionTest(){
        {
            long Aid = accountEntityFacade.create("CHECK", "blablablabla", "asfaagsagsadgdag");
            long Tid = transactionEntityFacade.create(Constants.TRANSACTION_TYPE_CREDIT, 1000, Constants.TRANSACTION_STATUS_OK);
            Assert.assertNotNull(accountEntityFacade.find(Aid));
            Assert.assertNotNull(transactionEntityFacade.find(Tid));
            
            try {accountEntityFacade.addTransaction(Aid, Tid);
                Assert.assertEquals(Aid, transactionEntityFacade.find(Tid).getAccount().getId());}
            catch (Exception ex){
                //Should not reach this
                Assert.assertTrue(false);
            }
            
            try {
                accountEntityFacade.addTransaction(Aid, (Tid + 1));
                //Should not reach this
                //Assert.assertTrue(false);
            } catch (Exception ex) {
                Assert.assertTrue(true);
            }
        }
        
    }
    
    @Test
    public void removeTest() throws Exception {
        long id = accountEntityFacade.create("CHECK", "sdvkdvsnvkn", "dccdasccas");
        Assert.assertTrue(accountEntityFacade.find(id) != null);
        
        try {
            accountEntityFacade.remove(id + 1);
            //Should not be reached
            Assert.assertTrue(false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
        
        accountEntityFacade.remove(id); 
        
        Assert.assertTrue(accountEntityFacade.find(id) == null);
    }
}