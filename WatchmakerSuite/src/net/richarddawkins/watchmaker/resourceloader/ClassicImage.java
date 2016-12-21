package net.richarddawkins.watchmaker.resourceloader;

import java.awt.image.BufferedImage;
import java.util.StringTokenizer;

public class ClassicImage {
 private BufferedImage image;
 String imageName;
 String description;
 String resourceType;
 String resourceId;
 String resolution;
 int advertisedWidth;
 int advertisedHeight;
 public ClassicImage(BufferedImage image, String imageName) {
   try {
  	 this.setImage(image);
  	 this.imageName = imageName;
  	 StringTokenizer st = new StringTokenizer(imageName, "_");
  	 description = st.nextToken();
  	 resourceType = st.nextToken();
  	 resourceId = st.nextToken();
  	 resolution = st.nextToken();
  	 st = new StringTokenizer(resolution, "x");
  	 advertisedWidth = Integer.parseInt(st.nextToken());
  	 advertisedHeight = Integer.parseInt(st.nextToken());
   } catch (Exception e) {
     System.out.println("Error while loading image " + imageName);
   }
			 
 }
public BufferedImage getImage() {
	return image;
}
public void setImage(BufferedImage image) {
	this.image = image;
}
}
