/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.facade;

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
import se.liu.ida.tdp024.account.util.exceptions.EntityNotFoundException;
import se.liu.ida.tdp024.account.util.exceptions.InputParameterException;
import se.liu.ida.tdp024.account.util.exceptions.ServiceConfigurationException;

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
    public void createTest() throws InputParameterException, EntityNotFoundException, ServiceConfigurationException {
        long id = transactionLogicFacade.create("CREDIT", 1000, "OK");
        
        Transaction transaction = transactionEntityFacade.find(id);
        Assert.assertTrue(  transaction != null &&
                            transaction.getAmount() == 1000 &&
                            transaction.getCreated() != null);
    }
    
    @Test
    public void findTest() throws InputParameterException, EntityNotFoundException, ServiceConfigurationException {
        long id = transactionLogicFacade.create("CREDIT", 2000, "OK");
        
        transactionLogicFacade.find(id);
    }
    
    @Test
    public void findAllTest() throws InputParameterException, ServiceConfigurationException {
        storageFacade.emptyStorage();
        
        transactionLogicFacade.create("CREDIT", 2000, "OK");
        transactionLogicFacade.create("DEBIT", 200, "OK");
        transactionLogicFacade.create("CREDIT", 3000, "OK");
        transactionLogicFacade.create("CREDIT", 2000, "FAILED");
        
        Assert.assertTrue(transactionLogicFacade.findAll().size() == 4);
    }
    
    @Test public void updateTest() throws InputParameterException, EntityNotFoundException, ServiceConfigurationException {
        long id = transactionLogicFacade.create("CREDIT", 2000, "OK");        
        transactionLogicFacade.update(id, "CREDIT", 1000, "OK");        
        Transaction transaction = transactionLogicFacade.find(id);
        
        Assert.assertTrue(transaction.getAmount() == 1000);
    }
    
    @Test
    public void removeTest() throws InputParameterException, EntityNotFoundException, ServiceConfigurationException {
        long id = transactionLogicFacade.create("CREDIT", 2000, "OK");
        transactionLogicFacade.remove(id);
        
        try {
            transactionLogicFacade.find(id);
            Assert.assertTrue(false);
                }
        catch(EntityNotFoundException e){
            Assert.assertTrue(true);
        }
    }
}
