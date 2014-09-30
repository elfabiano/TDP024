/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.facade;

import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class TransactionEntityFacadeTest {
    
    private final TransactionEntityFacade transactionEntityFacade = 
            new TransactionEntityFacadeDB();
    private final StorageFacade storageFacade = new StorageFacadeDB();
     
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    @SuppressWarnings("UnusedAssignment")
    public void createTest() {
        String type = "DEBIT";
        int amount = 5000;
        SimpleDateFormat time = new SimpleDateFormat();
        String status = "OK";
        long id;
        id = -1;
        
        id = transactionEntityFacade.create(type, amount, time, status);

        Assert.assertFalse(-1 == id);
    }
    
    @Test
    public void findTest() {
        long id = transactionEntityFacade.create("DEBIT", 4000,
                                new SimpleDateFormat(), "OK");
        
        Assert.assertTrue(transactionEntityFacade.find(id) != null);
    }
    
    @Test
    public void findAllTest() {
        storageFacade.emptyStorage();
        
        transactionEntityFacade.create("DEBIT", 5000, new SimpleDateFormat(), "OK");
        transactionEntityFacade.create("CREDIT", 4000, new SimpleDateFormat(), "OK");
        transactionEntityFacade.create("DEBIT", 3000, new SimpleDateFormat(), "OK");
        transactionEntityFacade.create("DEBIT", 2000, new SimpleDateFormat(), "OK");
        transactionEntityFacade.create("DEBIT", 1000, new SimpleDateFormat(), "OK");
        
        List<Transaction> transactions = transactionEntityFacade.findAll();
        
        Assert.assertTrue(transactions.size() == 5);
    }
    
    @Test
    public void updateTest() {
        long id = transactionEntityFacade.create("DEBIT", 5000,
                new SimpleDateFormat(), "OK");
        transactionEntityFacade.update(id, "CREDIT", 5000,
                new SimpleDateFormat(), "OK");
        Transaction transaction = transactionEntityFacade.find(id);
        
        Assert.assertTrue(transaction.getType().equals("CREDIT"));
    }
    
    @Test
    public void removeTest() {
        long id = transactionEntityFacade.create("CREDIT", 2000,
                new SimpleDateFormat(), "OK");
        
        transactionEntityFacade.remove(id);
        
        Assert.assertTrue(transactionEntityFacade.find(id) == null);
    }
}
