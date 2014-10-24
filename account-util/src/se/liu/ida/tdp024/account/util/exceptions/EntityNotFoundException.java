/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.util.exceptions;

/**
 *
 * @author filipschollin
 */
public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(){
        super();
    }
    public EntityNotFoundException(String message){
        super(message);
    }
}
