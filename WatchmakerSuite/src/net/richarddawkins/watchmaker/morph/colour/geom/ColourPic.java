package net.richarddawkins.watchmaker.morph.colour.geom;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Lin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morph.mono.geom.MonoPic;

public class ColourPic extends MonoPic {

    protected int backGroundColor;

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
            Lin movePtr = new ColourLin(new Point(x, y), new Point(xnew, ynew), thickness, color);
            addLin(movePtr);
        }
    }

    protected void limbRect(Graphics2D g2, ColourLin line, Rectangle square) {
        g2.drawRect(square.x, square.y, square.width, square.height);
        if (line.limbFill == LimbFillType.Filled)
            g2.fillRect(square.x, square.y, square.width, square.height);
    }

    protected void limbOval(Graphics2D g2, ColourLin line, Rectangle square) {
        g2.drawOval(square.x, square.y, square.width, square.height);
        if (line.limbFill == LimbFillType.Filled)
            g2.fillOval(square.x, square.y, square.width, square.height);
    }
    @Override
    protected void limb(Graphics2D g2, Lin line) {

        ColourLin colourLin = (ColourLin) line;
        g2.setColor(new Color(colourLin.color));
        Rectangle square = new Rectangle(Math.min(line.startPt.h, line.endPt.h), Math.min(line.startPt.v, line.endPt.v),
                Math.abs(line.endPt.h - line.startPt.h), Math.abs(line.endPt.v - line.startPt.v));

        switch (colourLin.limbShape) {
        case Oval:
            limbOval(g2, colourLin, square);
        case Rectangle:
            limbRect(g2, colourLin, square);
        default:
        }
        g2.drawLine(line.startPt.h, line.startPt.v, line.endPt.h, line.endPt.v);
    }
}
