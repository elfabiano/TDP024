package tdp024.account.rest.service;

import java.util.Map;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.rest.service.AccountService;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

public class AccountServiceTest {

    //-- Units under test ---//
    private StorageFacade storageFacade = new StorageFacadeDB();
    
    private AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();

    }

    @Test
    public void testCreate() {
        AccountService accS1= new AccountService();
        String AccType = "CHECK";
        String person = "Lisa Lisasson";
        String bank = "SWEDBANK";
        
        Response response = accS1.create(AccType, person, bank);
        Assert.assertEquals(200, response.getStatus());  
        Assert.assertEquals("OK", response.getEntity());
        
        AccType = "SAVINGS";
        person = "Blabla";
        bank = "SWEDBANK";
        
        response = accS1.create(AccType, person, bank);
        Assert.assertEquals(200, response.getStatus());  
        Assert.assertEquals("FAILED", response.getEntity());
        
        AccType = "SAVINGS";
        person = "Lisa Lisasson";
        bank = "EVIL";
        
        response = accS1.create(AccType, person, bank);
        Assert.assertEquals(200, response.getStatus());  
        Assert.assertEquals("FAILED", response.getEntity());
    }
    
    @Test
    public void testFind() {
        AccountService accS1= new AccountService();
        String AccType = "CHECK";
        String person = "Lisa Lisasson";
        String bank = "SWEDBANK";
        
        accS1.create(AccType, person, bank);
        Response response = accS1.find("Lisa Lisasson");
        
        Map<String, String> obj = jsonSerializer.fromJson(response.getEntity().toString(), Map.class);
        System.out.println(response);
        Assert.assertEquals(200, response.getStatus());  
        Assert.assertEquals("CHECK", obj.get("accounttype"));
        
        response = accS1.find("Blabla");
        Assert.assertEquals(200, response.getStatus());  
        Assert.assertEquals("FAILED", response.getEntity());

    }
}