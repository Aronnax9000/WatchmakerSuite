package net.richarddawkins.watchmaker.swing.images;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.image.ClassicImage;

public class AWTClassicImage implements ClassicImage {
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.resourceloader.ClassicImage");

    protected BufferedImage image;
    protected String imageName;
    protected String description;
    protected String resourceType;
    protected String resourceId;
    protected String resolution;
    protected int advertisedWidth;
    protected int advertisedHeight;

    public AWTClassicImage(BufferedImage image, String imageName) {
        try {
            this.setImage(image);
            this.imageName = imageName;
//            StringTokenizer st = new StringTokenizer(imageName, "_");
//            description = st.nextToken();
//            resourceType = st.nextToken();
//            resourceId = st.nextToken();
//            resolution = st.nextToken();
//            st = new StringTokenizer(resolution, "x");
//            advertisedWidth = Integer.parseInt(st.nextToken());
//            advertisedHeight = Integer.parseInt(st.nextToken());
        } catch (Exception e) {
            logger.log(Level.INFO, "Error while loading image " 
                    + imageName + " :" + e);
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public String getResourceType() {
        return resourceType;
    }

    @Override
    public String getResourceId() {
        return resourceId;
    }

    @Override
    public String getImageName() {
        
        return imageName;
    }
}
