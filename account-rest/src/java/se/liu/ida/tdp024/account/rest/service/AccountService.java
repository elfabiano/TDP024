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
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

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
          return Response.ok().entity(Constants.TRANSACTION_STATUS_OK).build();
      }
      catch (Exception e){
          return Response.ok().entity(Constants.TRANSACTION_STATUS_FAILED).build();
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
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);            
        }      
      return Response.ok().entity(json).build();
  }
  
  @GET
  @Path("debit")
  public Response debit(@QueryParam("id") long id,
                        @QueryParam("amount") int amount) {  
      try {
        accountLogicFacade.debit(id, amount);
        return Response.ok().entity(Constants.TRANSACTION_STATUS_OK).build();
      } catch (Exception e) {
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, e);
          return Response.ok().entity(Constants.TRANSACTION_STATUS_FAILED).build();
      }
    }
  
  @GET
  @Path("credit")
  public Response credit(@QueryParam("id") long id,
                         @QueryParam("amount") int amount) {
      try {
        accountLogicFacade.credit(id, amount);
        return Response.ok().entity(Constants.TRANSACTION_STATUS_OK).build();
      } catch (Exception e) {
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, e);
          return Response.ok().entity(Constants.TRANSACTION_STATUS_FAILED).build();
      }
    }
  
  @GET
  @Path("transactions")
  public Response transactions(@QueryParam("id") long id) {
      String json = "[]";
      try {
        List<Transaction> transactions = accountLogicFacade.transactions(id);
        json = jsonSerializer.toJson(transactions);
      } catch (Exception ex){
          Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
      }
      return Response.ok().entity(json).build();
  }
}
