package se.liu.ida.tdp024.account.data.test.facade;

import java.text.SimpleDateFormat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();;
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
        String accType = "Debit";
        String personKey = "asgahfshs82";
        String bankKey = "blablablabla";
        
        long id = accountEntityFacade.create(accType, personKey, bankKey);
        System.out.println(id);
        Assert.assertFalse(id==0);
    }
    
    @Test
    public void findTest() {
        String accType = "Debit";
        String personKey = "asgahfshs82";
        String bankKey = "blablablabla";
        
        long id = accountEntityFacade.create(accType, personKey, bankKey);
        
        
        
        Assert.assertTrue(accountEntityFacade.find(id) != null);
    }
    
    @Test
    public void findAllTest() {

        accountEntityFacade.create("Debit", "badgadgadgadg", "agasgasgagasg");
        accountEntityFacade.create("Debit", "asfasfafadsfas", "sdfsdfsadfsfs");
        accountEntityFacade.create("Credit", "fbdfhdtjrths", "fadgsfbdfbdferghs");
        accountEntityFacade.create("Debit", "gsfbcgdgsdgs", "sdfsdfaweref");
        accountEntityFacade.create("Credit", "fhdfndfhsdfadf", "vadgfdhghgadg");
        
        
        Assert.assertTrue(accountEntityFacade.findAll() != null);
    }
    
    @Test
    public void findAllTestKey() {

        accountEntityFacade.create("Debit", "badgadgadgadg", "agasgasgagasg");
        accountEntityFacade.create("Debit", "asfasfafadsfas", "sdfsdfsadfsfs");
        accountEntityFacade.create("Credit", "badgadgadgadg", "fadgsfbdfbdferghs");
        accountEntityFacade.create("Debit", "gsfbcgdgsdgs", "sdfsdfaweref");
        accountEntityFacade.create("Credit", "fhdfndfhsdfadf", "vadgfdhghgadg");
        
        
        Assert.assertTrue(accountEntityFacade.findAll("badgadgadgadg") != null);
    }
    
    @Test
    public void updateAmount() {

        accountEntityFacade.create("Debit", "badgadgadgadg", "agasgasgagasg");
        accountEntityFacade.updateAmount(1, 30);
        System.out.println(accountEntityFacade.find(1).getHoldings());
        Assert.assertTrue(accountEntityFacade.find(1).getHoldings() != 0);
    }
}