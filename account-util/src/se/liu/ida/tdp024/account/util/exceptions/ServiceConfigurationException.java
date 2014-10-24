/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.util.exceptions;

/**
 *
 * @author filipschollin
 */
public class ServiceConfigurationException extends Exception{
    public ServiceConfigurationException(){
        super();
    }
    public ServiceConfigurationException(String message){
        super(message);
    }
}
