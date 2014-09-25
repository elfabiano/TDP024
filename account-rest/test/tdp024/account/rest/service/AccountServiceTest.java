package tdp024.account.rest.service;

import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.rest.service.AccountService;

public class AccountServiceTest {

    //-- Units under test ---//
    private StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();

    }

    @Test
    public void testCreate() {
        AccountService accS= new AccountService();
        String AccType = "Debit";
        String personKey = "pfadgadgadgavdgh";
        String bankKey = "bdgdbsbsfndfdgsdgsdg";
        
        Response response = accS.create(AccType, personKey, bankKey);
        
        Assert.assertEquals(200, response.getStatus());
        
        
    }
}