package net.richarddawkins.watchmaker.morphs.colour.geom;

import java.awt.Color;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.bio.geom.Lin;
import net.richarddawkins.watchmaker.morphs.bio.geom.Pic;
import net.richarddawkins.watchmaker.morphs.bio.geom.Point;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbShapeType;

public class ColourPic extends Pic {

    protected int backgroundColor;
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    protected int thickness;
    protected LimbFillType limbFill;
    protected LimbShapeType limbShape;
    

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public LimbFillType getLimbFill() {
        return limbFill;
    }

    public void setLimbFill(LimbFillType limbFill) {
        this.limbFill = limbFill;
    }

    public LimbShapeType getLimbShape() {
        return limbShape;
    }

    public void setLimbShape(LimbShapeType limbShape) {
        this.limbShape = limbShape;
    }

    public ColourPic(Morph morph) {
        super(morph);
    }

    int[] colorVals = { 0, 51, 102, 153, 204, 255 };
    int[] backColorVals = { 255, 204, 153, 102, 51, 0 };
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

    public void picLine(int x, int y, int xnew, int ynew, int thickness, int color) {
        if (lines.size() >= PICSIZEMAX) {

            // {Message(GetString(TooLargeString));}
            // {used the help dialog! v1.1 changed to alert}
            // DisplayError(-147, 'Biomorph too large, or other problem', '
            // ', StopError);
            // ExitToShell
        } else {
            Lin movePtr = new ColourLin(new Point(x, y), new Point(xnew, ynew), color);
            addLin(movePtr);
        }
    }

}
