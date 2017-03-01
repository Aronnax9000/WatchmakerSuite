package net.richarddawkins.watchmaker.swing;

import java.awt.Color;

import net.richarddawkins.watchmaker.geom.Palette;
import net.richarddawkins.watchmaker.geom.RGBTriple;
import net.richarddawkins.watchmaker.geom.WatchmakerColor;

public class SwingColor {
    
    private static Color toColor(RGBTriple triple) {
        return new Color(triple.r, triple.g, triple.b);
    }

    public static Color toColor(int index) {
        Palette palette = WatchmakerColor.getInstance().getPalette();
        RGBTriple[] rgbTriples = palette.getColors();
        int length = rgbTriples.length;
        RGBTriple rgbTriple = rgbTriples[index % length];
        return toColor(rgbTriple);
    }
}
