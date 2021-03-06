/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.util.exceptions.EntityNotFoundException;
import se.liu.ida.tdp024.account.util.exceptions.InputParameterException;
import se.liu.ida.tdp024.account.util.exceptions.ServiceConfigurationException;
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
    public void createTest() throws InputParameterException, ServiceConfigurationException {
        String type = "DEBIT";
        int amount = 5000;
        String status = "OK";
        long id;
        id = -1;

        id = transactionEntityFacade.create(type, amount, status);

        Assert.assertFalse(-1 == id);

    }

    @Test
    public void findTest() throws InputParameterException, EntityNotFoundException, ServiceConfigurationException {
        long id = transactionEntityFacade.create("DEBIT", 4000, "OK");

        Assert.assertTrue(transactionEntityFacade.find(id) != null);
    }

    @Test
    public void findFailTest() throws InputParameterException, ServiceConfigurationException {
        storageFacade.emptyStorage();
        long id = transactionEntityFacade.create("DEBIT", 4000, "OK");

        try {
            Transaction transaction = transactionEntityFacade.find(2);
            Assert.assertTrue(false);
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void findAllTest() throws InputParameterException, ServiceConfigurationException {
        storageFacade.emptyStorage();

        transactionEntityFacade.create("DEBIT", 5000, "OK");
        transactionEntityFacade.create("CREDIT", 4000, "OK");
        transactionEntityFacade.create("DEBIT", 3000, "OK");
        transactionEntityFacade.create("DEBIT", 2000, "OK");
        transactionEntityFacade.create("DEBIT", 1000, "OK");

        List<Transaction> transactions = transactionEntityFacade.findAll();

        Assert.assertTrue(transactions.size() == 5);
    }

    @Test
    public void updateTest() throws Exception {
        long id = transactionEntityFacade.create("DEBIT", 5000, "OK");
        transactionEntityFacade.update(id, "CREDIT", 5000, "OK");
        Transaction transaction = transactionEntityFacade.find(id);

        Assert.assertTrue(transaction.getType().equals("CREDIT"));
    }

    @Test
    public void updateFailTest() throws InputParameterException, EntityNotFoundException, ServiceConfigurationException {
        storageFacade.emptyStorage();
        long id = transactionEntityFacade.create("DEBIT", 5000, "OK");
        try {
            transactionEntityFacade.update(id + 1, "CREDIT", 5000, "OK");
            //Should not be reached
            Assert.assertTrue(false);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
        Transaction transaction = transactionEntityFacade.find(id);

        Assert.assertTrue(transaction.getType().equals("DEBIT"));
    }

    @Test
    public void removeTest() throws InputParameterException, EntityNotFoundException, ServiceConfigurationException {
        long id = transactionEntityFacade.create("CREDIT", 2000, "OK");

        transactionEntityFacade.remove(id);

        
        try {
            transactionEntityFacade.update(id, "CREDIT", 5000, "OK");
            //Should not be reached
            Assert.assertTrue(false);
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeFailTest() throws InputParameterException, ServiceConfigurationException, EntityNotFoundException {
        storageFacade.emptyStorage();
        long id = transactionEntityFacade.create("CREDIT", 2000, "OK");

        try {
            transactionEntityFacade.remove(id + 2);
            //Should not be reached
            Assert.assertTrue(false);
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
        transactionEntityFacade.remove(id);
        try {
            transactionEntityFacade.find(id);
            Assert.assertTrue(false);
        } catch (EntityNotFoundException e) {
            Assert.assertTrue(true);
        }
    }
}
