/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.util.exceptions;

/**
 *
 * @author filipschollin
 */
public class InputParameterException extends Exception{
    public InputParameterException(){
        super();
    }
    public InputParameterException(String message){
        super(message);
    }
}
