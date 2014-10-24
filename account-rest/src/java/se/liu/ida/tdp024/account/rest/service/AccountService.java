package se.liu.ida.tdp024.account.rest.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.Constants;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.exceptions.AccountBalanceException;
import se.liu.ida.tdp024.account.util.exceptions.EntityNotFoundException;
import se.liu.ida.tdp024.account.util.exceptions.InputParameterException;
import se.liu.ida.tdp024.account.util.exceptions.ServiceConfigurationException;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

/*Errors:
    200 - OK
*   201 - Created
*   400 - Bad request (Input parameter exception)
*   500 - Internal server error
*   404 - Resource not found
*/
@Path("/account")
public class AccountService {
    private final AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB()); 
    
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();

  @GET
  @Path("create")
  public Response create(   @QueryParam("accounttype") String accountType,
                            @QueryParam("name") String name,
                            @QueryParam("bank") String bank) {
      
      try {
          accountLogicFacade.create(accountType, name, bank);
          return Response.status(201).build();
      }
      catch (InputParameterException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(400).entity(ex.getMessage()).build();
      }
      catch (ServiceConfigurationException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(500).entity(ex.getMessage()).build();
      }
  }
  
  @GET
  @Path("find/name")
  public Response find(@QueryParam("name") String name) {
      String json = "[]";
      try {
            List<Account> accounts;
            accounts = accountLogicFacade.find(name);
            json = jsonSerializer.toJson(accounts);
        } catch (InputParameterException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(400).entity(ex.getMessage()).build();
      }
      catch (ServiceConfigurationException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(500).entity(ex.getMessage()).build();
      }    
      return Response.ok().entity(json).build();
  }
  
  @GET
  @Path("debit")
  public Response debit(@QueryParam("id") long id,
                        @QueryParam("amount") int amount) {  
      try {
        accountLogicFacade.debit(id, amount);        
      } catch (InputParameterException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(400).build();
      }
      catch (ServiceConfigurationException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(500).entity(ex.getMessage()).build();
      } catch (EntityNotFoundException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(404).entity(ex.getMessage()).build();
      }
      catch (AccountBalanceException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(400).entity(ex.getMessage()).build();
      }
      
      return Response.ok().build();
    }
  
  @GET
  @Path("credit")
  public Response credit(@QueryParam("id") long id,
                         @QueryParam("amount") int amount) {
      try {
        accountLogicFacade.credit(id, amount);
      } catch (InputParameterException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(400).build();
      }
      catch (ServiceConfigurationException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(500).entity(ex.getMessage()).build();
      } catch (EntityNotFoundException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(404).entity(ex.getMessage()).build();
      }
      catch (AccountBalanceException ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
          return Response.status(400).entity(ex.getMessage()).build();
      }
      return Response.ok().build();
    }
  
  @GET
  @Path("transactions")
  public Response transactions(@QueryParam("id") long id) {
      String json = "[]";
      try {
        List<Transaction> transactions = accountLogicFacade.transactions(id);
        json = jsonSerializer.toJson(transactions);
      } catch (EntityNotFoundException ex) { 
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (InputParameterException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(400).entity(ex.getMessage()).build();
        } 
      return Response.ok().entity(json).build();
  }
}
