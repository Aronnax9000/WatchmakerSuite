package net.richarddawkins.watchmaker.morphview;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class MorphViewFactoryService {
    private static MorphViewFactoryService service;
    private ServiceLoader<MorphViewFactory> loader;
    private MorphViewFactory factory;

    private MorphViewFactoryService() {
        loader = ServiceLoader.load(MorphViewFactory.class);
        try {
            Iterator<MorphViewFactory> factories = loader.iterator();
            if (factories.hasNext()) {
                factory = factories.next();
            }
        } catch (ServiceConfigurationError serviceError) {
            factory = null;
            serviceError.printStackTrace();
        }
    }

    public static synchronized MorphViewFactoryService getInstance() {
        if (service == null) {
            service = new MorphViewFactoryService();
        }
        return service;
    }

    public MorphViewFactory getFactory() {
        return factory;
    }}
