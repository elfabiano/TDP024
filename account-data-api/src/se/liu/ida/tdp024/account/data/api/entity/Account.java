/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author fabwi272
 */
public interface Account extends Serializable {

    /**
     * @return the accountType
     */
    String getAccountType();

    /**
     * @return the bankKey
     */
    String getBankKey();

    /**
     * @return the holdings
     */
    int getHoldings();

    /**
     * @return the id
     */
    long getId();

    /**
     * @return the personKey
     */
    String getPersonKey();
    
    List<Transaction> getTransactions();

    /**
     * @param accountType the accountType to set
     */
    void setAccountType(String accountType);

    /**
     * @param bankKey the bankKey to set
     */
    void setBankKey(String bankKey);

    /**
     * @param holdings the holdings to set
     */
    void setHoldings(int holdings);

    /**
     * @param id the id to set
     */
    void setId(long id);

    /**
     * @param personKey the personKey to set
     */
    void setPersonKey(String personKey);
    
    void setTransactions(List<Transaction> transactions);
}
