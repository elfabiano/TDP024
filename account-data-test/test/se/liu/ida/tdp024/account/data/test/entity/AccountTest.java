/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.entity;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;

/**
 *
 * @author Fabian
 */
public class AccountTest {
    Account account = new AccountDB();
    
    @Test
    public void setTransactionsTest() {
        account.setTransactions(null);
         Assert.assertTrue(account.getTransactions() == null);
         
        account.setTransactions(new ArrayList<Transaction>());
        Assert.assertTrue(account.getTransactions() != null);
    }
}
