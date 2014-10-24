/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.util.exceptions.EntityNotFoundException;
import se.liu.ida.tdp024.account.util.exceptions.InputParameterException;
import se.liu.ida.tdp024.account.util.exceptions.ServiceConfigurationException;

public interface TransactionLogicFacade {
    public long create(String type, int amount, String status) throws InputParameterException, ServiceConfigurationException;
    
    public Transaction find(long id) throws InputParameterException, EntityNotFoundException;
    
    public List<Transaction> findAll() throws ServiceConfigurationException;
    
    public void update(long id, String type, int amount, String status) throws 
            EntityNotFoundException, 
            ServiceConfigurationException;
    
    public void remove(long id) throws 
            EntityNotFoundException, 
            InputParameterException, 
            ServiceConfigurationException;
}
