/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.test.rules;

import junit.framework.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.rules.TransactionVerification;
import se.liu.ida.tdp024.account.logic.impl.constants.Constants;
import se.liu.ida.tdp024.account.logic.impl.rules.BasicTransactionVerification;

/**
 *
 * @author Fabian
 */
public class TransactionVerifiactionTest {
    TransactionVerification transactionVerification = new BasicTransactionVerification();
    StorageFacade storageFacade = new StorageFacadeDB();    
        
    @Test
    public void isAcceptableTest() {
        Account account = new AccountDB();
        
        account.setAccountType(Constants.ACCOUNT_TYPE_CHECK);
        account.setHoldings(10000);
        
        Assert.assertTrue(transactionVerification.isAcceptable(
                            Constants.TRANSACTION_TYPE_DEBIT, 9999, account));
        Assert.assertTrue(transactionVerification.isAcceptable(
                            Constants.TRANSACTION_TYPE_DEBIT, 10000, account));
        Assert.assertFalse(transactionVerification.isAcceptable(
                            Constants.TRANSACTION_TYPE_DEBIT, 10001, account));
        Assert.assertTrue(transactionVerification.isAcceptable(
                            Constants.TRANSACTION_TYPE_CREDIT, 10001, account));
    }
}
