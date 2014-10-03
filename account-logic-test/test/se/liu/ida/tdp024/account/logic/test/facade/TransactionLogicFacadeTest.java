/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.Date;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;

public class TransactionLogicFacadeTest {
    private TransactionLogicFacade transactionLogicFacade;    
    private TransactionEntityFacade transactionEntityFacade;
    private StorageFacade storageFacade;
    
    @Before
    public void setup() {
        transactionEntityFacade = new TransactionEntityFacadeDB();
        transactionLogicFacade = new TransactionLogicFacadeImpl(transactionEntityFacade);
        storageFacade = new StorageFacadeDB();
    }
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void createTest() {
        long id = transactionLogicFacade.create("CREDIT", 1000, "OK");
        
        Transaction transaction = transactionEntityFacade.find(id);
        Assert.assertTrue(  transaction != null &&
                            transaction.getAmount() == 1000 &&
                            transaction.getTime() != null);
    }
    
    @Test
    public void findTest() {
        long id = transactionLogicFacade.create("CREDIT", 2000, "OK");
        
        Assert.assertTrue(transactionLogicFacade.find(id) != null);
    }
    
    @Test
    public void findAllTest() {
        storageFacade.emptyStorage();
        
        transactionLogicFacade.create("CREDIT", 2000, "OK");
        transactionLogicFacade.create("DEBIT", 200, "OK");
        transactionLogicFacade.create("CREDIT", 3000, "OK");
        transactionLogicFacade.create("CREDIT", 2000, "FAILED");
        
        Assert.assertTrue(transactionLogicFacade.findAll().size() == 4);
    }
    
    @Test public void updateTest() {
        long id = transactionLogicFacade.create("CREDIT", 2000, "OK");        
        transactionLogicFacade.update(id, "CREDIT", 1000, new Date(), "OK");        
        Transaction transaction = transactionLogicFacade.find(id);
        
        Assert.assertTrue(transaction.getAmount() == 1000);
    }
    
    @Test
    public void removeTest() {
        long id = transactionLogicFacade.create("CREDIT", 2000, "OK");
        transactionLogicFacade.remove(id);
        
        Assert.assertTrue(transactionLogicFacade.find(id) == null);
    }
}
