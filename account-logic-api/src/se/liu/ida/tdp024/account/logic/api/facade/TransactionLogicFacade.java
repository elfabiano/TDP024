/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.Date;
import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface TransactionLogicFacade {
    public long create(String type, int amount, String status);
    
    public Transaction find(long id);
    
    public List<Transaction> findAll();
    
    public void update(long id, String type, int amount, Date time, String status);
    
    public void remove(long id);
}
