/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.api.rules;

import se.liu.ida.tdp024.account.data.api.entity.Account;

/**
 *
 * @author Fabian
 */
public interface TransactionVerification {
    public boolean isAcceptable(String transactionType, int amount, Account account);
}
