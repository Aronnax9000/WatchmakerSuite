package net.richarddawkins.watchmaker.swing.images;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import net.richarddawkins.watchmaker.image.ClassicImage;
import net.richarddawkins.watchmaker.image.ClassicImageLoader;

public class SwingClassicImageLoader  implements ClassicImageLoader {
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.images.SwingClassicImageLoader");
    
	Vector<ClassicImage> classicImages = new Vector<ClassicImage>();

    HashMap<String, ClassicImage> imageMap = new HashMap<String, ClassicImage>();
    private static ClassicImageLoader instance = null;


    HashMap<String, HashMap<String, ClassicImage>> resources = new HashMap<String, HashMap<String, ClassicImage>>();

    
    
    void add(ClassicImage image) {
        String resourceType = image.getResourceType();
        HashMap<String, ClassicImage> resourceIdMap = resources.get(resourceType);
        if (resourceIdMap == null) {
            resourceIdMap = new HashMap<String, ClassicImage>();
            resources.put(resourceType, resourceIdMap);
        }
        resourceIdMap.put(image.getResourceId(), image);
        imageMap.put(image.getImageName(), image);
    }

    public ClassicImage getGeneratingCurve(int index) {
        switch (index) {
        case 128:
            return getPicture("SnailOutline128ConeWhelkARGB_PICT_00128_113x171");
        case 130:
            return getPicture("SnailOutline130JapaneseWonderARGB_PICT_00130_105x176");
        case 132:
            return getPicture("SnailOutline132RapaARGB_PICT_00132_104x181");
        case 134:
            return getPicture("SnailOutline134FigTunARGB_PICT_00134_77x212");
        case 136:
            return getPicture("SnailOutline136GallaghersARGB_PICT_00136_78x141");
        case 138:
            return getPicture("SnailOutline138RazorShellARGB_PICT_00138_46x214");
        case 140:
            return getPicture("SnailOutline140UnnamedARGB_PICT_00140_122x145");
        case 142:
            return getPicture("SnailOutline142UnnamedARGB_PICT_00142_78x181");
        case 144:
            return getPicture("SnailOutline144UnnamedARGB_PICT_00144_164x201");
        case 146:
            return getPicture("SnailOutline146EloiseARGB_PICT_00146_91x172");
        case 148:
            return getPicture("SnailOutline148ScallopARGB_PICT_00148_232x231");
        case 150:
            return getPicture("SnailOutline150LightningARGB_PICT_00150_57x173");
        case 152:
            return getPicture("SnailOutline152SundialARGB_PICT_00152_259x171");
        case 154:
            return getPicture("SnailOutline154UnnamedARGB_PICT_00154_68x173");
        default:
            return null;
        }
    }


    public ClassicImage getPicture(String imageName) {
        return imageMap.get(imageName);
    }
    public ClassicImage getPicture(String resourceType, String resourceId) {
        return resources.get(resourceType).get(resourceId);
    }
    public SwingClassicImageLoader() {
        for (String imageName : imageNames) {
            try {
                ClassicImage classicImage = new AWTClassicImage(
                        ImageIO.read(
                                ClassicImageLoader.class
                                .getResource("/net/richarddawkins/watchmaker/img/" 
                                + imageName 
                                + ".png")), imageName);
                classicImages.add(classicImage);
                add(classicImage);
            } catch (IOException e) {
                System.err.println("Error while trying to load " 
            + imageName 
            + ": " + e.getMessage());
            }
        }
    }

}
