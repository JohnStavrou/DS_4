package com.mycompany.instafacerestserver;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig; 
 
@ApplicationPath("api")
public class ApplicationContext extends ResourceConfig
{
    public ApplicationContext()
    {
        packages("com.mycompany.instafacerestserver");
        register(RestServer.class);
    }
}