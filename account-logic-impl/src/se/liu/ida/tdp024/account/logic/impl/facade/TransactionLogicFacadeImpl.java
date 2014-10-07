/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;


/**
 *
 * @author fabwi272
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade {
    private final TransactionEntityFacade transactionEntityFacade;
    
    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade) {
        this.transactionEntityFacade = transactionEntityFacade;
    }

    @Override
    public long create(String type, int amount, String status) {
        return transactionEntityFacade.create(type, amount, status);
    }
    
    @Override
    public Transaction find(long id) {
        return transactionEntityFacade.find(id);        
    }
    
    @Override
    public List<Transaction> findAll() {
        return transactionEntityFacade.findAll();
    }

    @Override
    public void update(long id, String type, int amount, String status) {
        transactionEntityFacade.update(id, type, amount, status);
    }

    @Override
    public void remove(long id) {
        transactionEntityFacade.remove(id);
    }

}
