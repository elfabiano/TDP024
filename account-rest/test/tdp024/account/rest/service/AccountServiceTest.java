package tdp024.account.rest.service;

import java.util.List;
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
        Assert.assertEquals(201, response.getStatus());  
        
        AccType = "SAVINGS";
        person = "Blabla";
        bank = "SWEDBANK";
        
        response = accS1.create(AccType, person, bank);
        Assert.assertEquals(400, response.getStatus());  
        
        AccType = "SAVINGS";
        person = "Lisa Lisasson";
        bank = "EVIL";
        
        response = accS1.create(AccType, person, bank);
        Assert.assertEquals(400, response.getStatus());  
    }
    
    @Test
    public void testFind() {
        AccountService accS1= new AccountService();
        String AccType = "CHECK";
        String person = "Lisa Lisasson";
        String bank = "SWEDBANK";
        {
            Response response = accS1.create(AccType, person, bank);
            Assert.assertEquals(201, response.getStatus());
        }
        {
            Response response = accS1.find("Lisa Lisasson");
            Assert.assertEquals(200, response.getStatus());
            
            String json = response.getEntity().toString();
            
            List<Map<String, String>> obj = jsonSerializer.fromJson(json, List.class); 

            Assert.assertNotNull(obj);
            Assert.assertEquals("CHECK", obj.get(0).get("accountType"));
        }
        {
            Response response = accS1.find("Blaaa");
            Assert.assertEquals(400, response.getStatus());
            
        }
    }
    
    @Test
    public void testDebit() {
        storageFacade.emptyStorage();
        
        AccountService accS1= new AccountService();
        String AccType = "CHECK";
        String person = "Lisa Lisasson";
        String bank = "SWEDBANK";
        {
            Response response = accS1.create(AccType, person, bank);
            Assert.assertEquals(201, response.getStatus());
        }
        {
            Response response = accS1.credit(1, 700);
            Assert.assertEquals(200, response.getStatus());
            
            response = accS1.debit(1, 800);
            Assert.assertEquals(400, response.getStatus());
            response = accS1.debit(1, 300);
            Assert.assertEquals(200, response.getStatus());
            response = accS1.debit(1, 400);
            Assert.assertEquals(200, response.getStatus());
            
            //Must test if an account does not exist.
            response = accS1.debit(2, 400);
            Assert.assertEquals(404, response.getStatus());
        }
        
    }
    
    @Test
    public void testCredit() {
        storageFacade.emptyStorage();
        
        AccountService accS1= new AccountService();
        String AccType = "CHECK";
        String person = "Lisa Lisasson";
        String bank = "SWEDBANK";
        {
            Response response = accS1.create(AccType, person, bank);
            Assert.assertEquals(201, response.getStatus());
        }
        
        {
            Response response = accS1.credit(1, 700);
            Assert.assertEquals(200, response.getStatus());
            
            response = accS1.credit(2, 600);
            Assert.assertEquals(404, response.getStatus());
        }
    }
    
    @Test
    public void testTransactions() {
        AccountService accS1= new AccountService();
        String AccType = "CHECK";
        String person = "Lisa Lisasson";
        String bank = "SWEDBANK";
        
        {
            Response response = accS1.create(AccType, person, bank);
            Assert.assertEquals(201, response.getStatus());
        }
        {
            Response response = accS1.credit(1, 700);
            Assert.assertEquals(200, response.getStatus());
            response = accS1.debit(1, 800);
            Assert.assertEquals(400, response.getStatus());
            response = accS1.debit(1, 300);
            Assert.assertEquals(200, response.getStatus());
            response = accS1.debit(1, 400);
            Assert.assertEquals(200, response.getStatus());
        }
        
        {
            Response response = accS1.transactions(1);
            Assert.assertEquals(200, response.getStatus());
            
            String json = response.getEntity().toString();
            List<Map<String, String>> obj = jsonSerializer.fromJson(json, List.class);
            
            Assert.assertNotNull(obj);
            Assert.assertEquals("CREDIT", obj.get(0).get("type"));
            Assert.assertEquals("DEBIT", obj.get(2).get("type"));
            Assert.assertEquals("FAILED", obj.get(1).get("status"));
            
        }
        
        {
            Response response = accS1.transactions(3);
            Assert.assertEquals(404, response.getStatus());
        }
    }
}