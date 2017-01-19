package net.richarddawkins.watchmaker.swing.appdata;

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

    public SwingAppDataFactory getFactory() {
    	SwingAppDataFactory factory = null;

        try {
        	
            Iterator<SwingAppDataFactory> factories = loader.iterator();
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
