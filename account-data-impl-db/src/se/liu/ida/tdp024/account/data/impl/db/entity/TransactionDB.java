/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Date;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import se.liu.ida.tdp024.account.data.api.entity.Account;

/**
 *
 * @author fabwi272
 */
@Entity
public class TransactionDB implements Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type;
    private int amount;
    
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date time;
    private String status;

    @ManyToOne(targetEntity = AccountDB.class)
    @JsonManagedReference
    private Account account;

    /**
     * @return the id
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    @Override
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the amount
     */
    @Override
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the time
     */
    @Override
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    @Override
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the status
     */
    @Override
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the account
     */
    @Override
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    @Override
    public void setAccount(Account account) {
        this.account = account;
    }
    
    @PrePersist
    @PreUpdate       
    void modifiedAt() {
        this.time = new Date();
    }
    
}
