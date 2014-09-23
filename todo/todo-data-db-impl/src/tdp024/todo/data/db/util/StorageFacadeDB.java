package tdp024.todo.data.db.util;

import tdp024.todo.data.api.util.StorageFacade;

public class StorageFacadeDB implements StorageFacade {

    @Override
    public void emptyStorage() {
        EMF.close();
    }
    
}
