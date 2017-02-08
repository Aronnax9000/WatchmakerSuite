package net.richarddawkins.watchmaker.geom;

public class WatchmakerColor {
    public final static int BlackColor = getColorIndexByRGB666Coordinates(0, 0, 0);							
    public final static int WhiteColor = getColorIndexByRGB666Coordinates(5, 5, 5);
    public final static int RedColor = getColorIndexByRGB666Coordinates(5, 0, 0);
    public final static int GreenColor = getColorIndexByRGB666Coordinates(0, 5, 0);
    public final static int BlueColor = getColorIndexByRGB666Coordinates(0, 0, 5);
    public final static int CyanColor = getColorIndexByRGB666Coordinates(0, 5, 5);
    public final static int MagentaColor = getColorIndexByRGB666Coordinates(5, 0, 5);
    public final static int YellowColor = getColorIndexByRGB666Coordinates(5, 5, 0);
    
    public static int getColorIndexByRGB666Coordinates(int r, int g, int b) {
    	return r * 36 + g * 6 + b;
    }
    
    public static RGBTriple getRGBTripleForIndex(int index) {
    	if(index < 216) {
    		int r = index / 36;
    		int g = (index - (36 * r)) / 6;
    		int b = index % 6;
    		return new RGBTriple(255 - (5 - r) * 256/6,
    				255 - (5 - g) * 256/6,255 - (5 - b) * 256/6);
    	} else {
    		int indexMinus216 = index - 216;
    		int singleColorIntensity = 255 - (indexMinus216 % 10) * 25;
    		if(index >= 216 && index <= 216 + 10) {
    			return new RGBTriple(singleColorIntensity, 0, 0);
	    	} else if(index >= 226 && index <= 226 + 10) {
    			return new RGBTriple(0, singleColorIntensity, 0);
	    	} else if(index >= 236 && index <= 236 + 10) {
    			return new RGBTriple(0, 0, singleColorIntensity);
	    	} else {
    			return new RGBTriple(singleColorIntensity, singleColorIntensity, singleColorIntensity);
	    	}
    	}
    }
    
}
