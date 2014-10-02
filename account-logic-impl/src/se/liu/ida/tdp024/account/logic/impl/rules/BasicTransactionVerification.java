/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.rules;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.logic.api.rules.TransactionVerification;
import se.liu.ida.tdp024.account.logic.impl.constants.Constants;

/**
 *
 * @author Fabian
 */
public class BasicTransactionVerification implements TransactionVerification {

    @Override
    public boolean isAcceptable(String transactionType, int amount, Account account) {
        if(transactionType.equals(Constants.TRANSACTION_TYPE_DEBIT)) {
            return amount <= account.getHoldings();
        }
        else if(transactionType.equals(Constants.TRANSACTION_TYPE_CREDIT)) {
            return true;
        }
        return false;
    }
    
}
