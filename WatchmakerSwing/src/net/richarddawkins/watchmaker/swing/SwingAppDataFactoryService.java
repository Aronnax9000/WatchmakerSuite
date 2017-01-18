package net.richarddawkins.watchmaker.swing;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class SwingAppDataFactoryService {
	
	private static SwingAppDataFactoryService service;
    private ServiceLoader<SwingAppDataFactory> loader;

    private SwingAppDataFactoryService() {
        loader = ServiceLoader.load(SwingAppDataFactory.class);
    }

    public static synchronized SwingAppDataFactoryService getInstance() {
        if (service == null) {
            service = new SwingAppDataFactoryService();
        }
        return service;
    }

    public SwingAppDataFactory getFactory(String name) {
    	SwingAppDataFactory factory = null;

        try {
            Iterator<SwingAppDataFactory> factories = loader.iterator();
            while (factory == null && factories.hasNext()) {
                factory = factories.next();
                if(factory.getName().equals(name)) {
                	break;
                } else {
                	factory = null;
                }
                
            }
        } catch (ServiceConfigurationError serviceError) {
        	factory = null;
            serviceError.printStackTrace();

        }
        return factory;
    }


	

}
