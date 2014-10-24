/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.util.exceptions;

/**
 *
 * @author filipschollin
 */
public class AccountBalanceException extends Exception{
    public AccountBalanceException(){
        super();
    }
    public AccountBalanceException(String message){
        super(message);
    }
}
