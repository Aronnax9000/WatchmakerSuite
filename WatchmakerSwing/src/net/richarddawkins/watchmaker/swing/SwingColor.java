package net.richarddawkins.watchmaker.swing;

import java.awt.Color;

public class SwingColor {
    public static Color[] rgbColorPalette = new Color[256];

    static {
        // First 216 colors
        for (int r = 0; r < 6; r++)
            for (int g = 0; g < 6; g++)
                for (int b = 0; b < 6; b++)
                    rgbColorPalette[r * 36 + g * 6 + b] = new Color(255 - (5 - r) * 51, 255 - (5 - g) * 51,
                            255 - (5 - b) * 51);
        for (int i = 216; i < 216 + 10; i++)
            rgbColorPalette[i] = new Color((10 - (i - 216)) * 25, 0, 0);
        for (int i = 226; i < 226 + 10; i++)
            rgbColorPalette[i] = new Color(0, (10 - (i - 226)) * 25, 0);
        for (int i = 236; i < 236 + 10; i++)
            rgbColorPalette[i] = new Color(0, 0, (10 - (i - 236)) * 25);
        for (int i = 246; i < 246 + 10; i++)
            rgbColorPalette[i] = new Color((10 - (i - 246)) * 25, (10 - (i - 246)) * 25, (10 - (i - 246)) * 25);
    }

}
