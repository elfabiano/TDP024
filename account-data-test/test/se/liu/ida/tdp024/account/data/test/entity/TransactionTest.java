/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.entity;

import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;

/**
 *
 * @author Fabian
 */
public class TransactionTest {
    Transaction transaction = new TransactionDB();
    
    @Test
    public void setCreatedTest() {
        Date now = new Date();
        transaction.setCreated(now);
        Assert.assertTrue(transaction.getCreated() == now);
    }
}
