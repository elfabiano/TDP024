/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author fabwi272
 */
public interface Transaction extends Serializable {

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
    Date getCreated();

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
     * @param status the status to set
     */
    void setStatus(String status);

    /**
     * @param time the time to set
     */
    void setCreated(Date time);

    /**
     * @param type the type to set
     */
    void setType(String type);
    
}
