/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.entity;

import java.text.SimpleDateFormat;
import se.liu.ida.tdp024.account.data.api.entity.Account;

/**
 *
 * @author fabwi272
 */
public interface Transaction {

    /**
     * @return the account
     */
    Account getAccount();

    /**
     * @return the amount
     */
    int getAmount();

    /**
     * @return the id
     */
    long getId();

    /**
     * @return the status
     */
    String getStatus();

    /**
     * @return the time
     */
    SimpleDateFormat getTime();

    /**
     * @return the type
     */
    String getType();

    /**
     * @param account the account to set
     */
    void setAccount(Account account);

    /**
     * @param amount the amount to set
     */
    void setAmount(int amount);

    /**
     * @param id the id to set
     */
    void setId(long id);

    /**
     * @param status the status to set
     */
    void setStatus(String status);

    /**
     * @param time the time to set
     */
    void setTime(SimpleDateFormat time);

    /**
     * @param type the type to set
     */
    void setType(String type);
    
}
