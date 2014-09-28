/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.test.util;

import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class StorageFacadeTest {
    StorageFacade storageFacade = new StorageFacadeDB();
    
    @Test
    public void emptyStorageTest() {
        
    }
}
