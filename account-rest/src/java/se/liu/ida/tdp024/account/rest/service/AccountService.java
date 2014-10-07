package se.liu.ida.tdp024.account.rest.service;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.constants.Constants;
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
      
      String response = Constants.TRANSACTION_STATUS_FAILED;
      
      try {
          accountLogicFacade.create(accountType, name, bank);
          response = Constants.TRANSACTION_STATUS_OK;
      }
      catch (Exception e){
      }
      finally{
          return Response.ok().entity(response).build();
      }
  }
  
  @GET
  @Path("find/name")
  public Response find(@QueryParam("name") String name) {
      List<Account> accounts = accountLogicFacade.find(name);
      String json = jsonSerializer.toJson(accounts);
      
      return Response.ok().entity(json).build();
  }
  
  @GET
  @Path("debit")
  public Response debit(@QueryParam("id") long id,
                        @QueryParam("amount") int amount) {  
      String response = Constants.TRANSACTION_STATUS_FAILED;
      try {
        accountLogicFacade.debit(id, amount);
        response = Constants.TRANSACTION_STATUS_OK;
      } catch (Exception e) {
      }
      finally{
          return Response.ok().entity(response).build();
      }
    }
  
  @GET
  @Path("credit")
  public Response credit(@QueryParam("id") long id,
                         @QueryParam("amount") int amount) {
      
      String response = Constants.TRANSACTION_STATUS_FAILED;
      try {
        accountLogicFacade.credit(id, amount);
        response = Constants.TRANSACTION_STATUS_OK;
      } catch (Exception e) {
          return Response.notModified().build();
      }
      finally{
          return Response.ok().entity(response).build();
      }
    }
  
  @GET
  @Path("transactions")
  public Response transactions(@QueryParam("id") long id) {
      List<Transaction> transactions = accountLogicFacade.transactions(id);
      
      String json = jsonSerializer.toJson(transactions);
      
      return Response.ok().entity(json).build();
  }
}
