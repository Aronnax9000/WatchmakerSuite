package net.richarddawkins.watchmaker.menu;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class WatchmakerMenuBuilderService {
    private static WatchmakerMenuBuilderService service;
    private ServiceLoader<WatchmakerMenuBuilder> loader;

    private WatchmakerMenuBuilderService() {
        loader = ServiceLoader.load(WatchmakerMenuBuilder.class);
    }

    public static synchronized WatchmakerMenuBuilderService getInstance() {
        if (service == null) {
            service = new WatchmakerMenuBuilderService();
        }
        return service;
    }

    public WatchmakerMenuBuilder getWatchmakerMenuBuilder() {
        WatchmakerMenuBuilder watchmakerMenuBuilder = null;
        try {
            
            Iterator<WatchmakerMenuBuilder> watchmakerMenuBuilders = loader.iterator();
            if (watchmakerMenuBuilders.hasNext()) {
                watchmakerMenuBuilder = watchmakerMenuBuilders.next();
            }
        } catch (ServiceConfigurationError serviceError) {
            watchmakerMenuBuilder = null;
            serviceError.printStackTrace();

        }
        return watchmakerMenuBuilder;
    }

}
