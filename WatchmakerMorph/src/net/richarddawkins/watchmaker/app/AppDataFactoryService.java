package net.richarddawkins.watchmaker.app;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class AppDataFactoryService {
	
	private static AppDataFactoryService service;
    private ServiceLoader<AppDataFactory> loader;

    private AppDataFactoryService() {
    	
        loader = ServiceLoader.load(AppDataFactory.class);
    }

    public static synchronized AppDataFactoryService getInstance() {
        if (service == null) {
            service = new AppDataFactoryService();
        }
        return service;
    }

    public AppDataFactory getFactory() {
    	AppDataFactory factory = null;

        try {
        	
            Iterator<AppDataFactory> factories = loader.iterator();
            if (factories.hasNext()) {
                factory = factories.next();
            }
        } catch (ServiceConfigurationError serviceError) {
        	factory = null;
            serviceError.printStackTrace();

        }
        return factory;
    }


	

}
