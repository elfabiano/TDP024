/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;

/**
 *
 * @author fabwi272
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade {
    private TransactionEntityFacade transactionEntityFacade;
    
    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade) {
        this.transactionEntityFacade= transactionEntityFacade;
    }
}
