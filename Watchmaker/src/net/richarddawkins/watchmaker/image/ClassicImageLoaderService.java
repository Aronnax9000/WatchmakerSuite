package net.richarddawkins.watchmaker.image;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class ClassicImageLoaderService {
	
	private static ClassicImageLoaderService service;
    private ServiceLoader<ClassicImageLoader> loader;

    private ClassicImageLoaderService() {
        loader = ServiceLoader.load(ClassicImageLoader.class);
    }

    public static synchronized ClassicImageLoaderService getInstance() {
        if (service == null) {
            service = new ClassicImageLoaderService();
        }
        return service;
    }

    public ClassicImageLoader getClassicImageLoader() {
        ClassicImageLoader classicImageLoader = null;
        try {
        	
            Iterator<ClassicImageLoader> classicImageLoaders = loader.iterator();
            if (classicImageLoaders.hasNext()) {
                classicImageLoader = classicImageLoaders.next();
            }
        } catch (ServiceConfigurationError serviceError) {
            classicImageLoader = null;
            serviceError.printStackTrace();

        }
        return classicImageLoader;
    }


	

}
