package net.richarddawkins.watchmaker.swing;

import java.awt.Color;

import net.richarddawkins.watchmaker.geom.RGBTriple;
import net.richarddawkins.watchmaker.geom.WatchmakerColor;

public class SwingColor {
    public static Color[] rgbColorPalette = new Color[256];
    
    static {
    	for(int i = 0; i < rgbColorPalette.length; i++) {
    		RGBTriple triple = WatchmakerColor.getRGBTripleForIndex(i);
    		rgbColorPalette[i] = new Color(triple.r, triple.g, triple.b);
    	}
    }

}
