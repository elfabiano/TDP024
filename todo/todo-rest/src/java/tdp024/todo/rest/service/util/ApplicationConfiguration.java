package tdp024.todo.rest.service.util;

import com.sun.jersey.api.core.PackagesResourceConfig;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class ApplicationConfiguration extends PackagesResourceConfig {
    
    public ApplicationConfiguration() {
        super("tdp024.todo.rest.service");
    }
    
}
