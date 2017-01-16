package net.richarddawkins.watchmaker.morph.colour.geom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Lin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.biomorph.geom.gui.SimpleSwingPic;
import net.richarddawkins.watchmaker.morph.colour.ColourBiomorph;
import net.richarddawkins.watchmaker.morph.colour.genome.ColourGenome;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbShapeType;

public class ColourPic extends SimpleSwingPic {

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

    void limb(Graphics2D g2, int x0, int y0, int x1, int y1, ColourBiomorph morph) {
        Rectangle square = null;
        ColourGenome genome = (ColourGenome) morph.getGenome();
        if (genome.getLimbShapeGene().getValue() == LimbShapeType.Oval
                || genome.getLimbShapeGene().getValue() == LimbShapeType.Rectangle)
            square = new Rectangle(Math.min(x0, x1), Math.min(y0, y1), Math.abs(x1 - x0), Math.abs(y1 - y0));

        g2.setStroke(new BasicStroke(genome.getThicknessGene().getValue()));
        switch (genome.getLimbShapeGene().getValue()) {
        case Oval:
            g2.drawOval(square.x, square.y, square.width, square.height);
            if (genome.getLimbFillGene().getValue() == LimbFillType.Filled)
                g2.fillOval(square.x, square.y, square.width, square.height);
            break;
        case Rectangle:
            g2.drawRect(square.x, square.y, square.width, square.height);
            if (genome.getLimbFillGene().getValue() == LimbFillType.Filled)
                g2.fillRect(square.x, square.y, square.width, square.height);
            break;
        default:
            break;
        }
        g2.drawLine(x0, y0, x1, y1);
        g2.setStroke(new BasicStroke(1.0f)); // Was MyPenSize
    }

    /**
     * FIXME
     * 
     * @param colorIndex
     * @return
     */
    public static Color chooseColor(int colorIndex) {
        return rgbColorPalette[colorIndex];
    }

    void actualLine(Graphics2D g2, PicStyleType picStyle, Compass orientation, Lin line,
            ColourBiomorph morph) {
        // int linColor;
        int vertOffset;
        int horizOffset;
        int x0, x1, y0, y1;

        if (orientation == Compass.NorthSouth) {
            vertOffset = origin.v;
            horizOffset = origin.h;
            y0 = line.startPt.v - vertOffset;
            y1 = line.endPt.v - vertOffset;
            x0 = line.startPt.h - horizOffset;
            x1 = line.endPt.h - horizOffset;
        } else {
            vertOffset = origin.h;
            horizOffset = origin.v;
            y0 = line.startPt.h - vertOffset;
            y1 = line.endPt.h - vertOffset;
            x0 = line.startPt.v - horizOffset;
            x1 = line.endPt.v - horizOffset;
        }
        g2.setColor(line.color);
        switch (picStyle) {
        case LF:
            limb(g2, x0, y0, x1, y1, morph);
        case RF:
            limb(g2, - x0, y0, - x1, y1, morph);
        case FF:
            limb(g2, x0, y0, x1, y1, morph);
            limb(g2, - x0, y0, - x1, y1, morph);
        case LUD:
            limb(g2, x0, y0, x1, y1, morph);
            limb(g2, - x0,  - y0,  - x1,  - y1, morph);
        case RUD:
            limb(g2, - x0, y0,  - x1, y1, morph);
            limb(g2, x0, - y0, x1,  - y1, morph);
        case FUD:
            limb(g2, x0, y0, x1, y1, morph);
            limb(g2,  - x0, y0,  - x1, y1, morph);
            limb(g2, x0,  - y0, x1,  - y1, morph);
            limb(g2,  - x0,  - y0,  - x1,  - y1, morph);
        default:
            break;
        }
    }

    public void picLine(int x, int y, int xnew, int ynew, int thickness, Color color) {

        if (lines.size() >= PICSIZEMAX) {
            
            // {Message(GetString(TooLargeString));}
            // {used the help dialog! v1.1 changed to alert}
            // DisplayError(-147, 'Biomorph too large, or other problem', '
            // ', StopError);
            // ExitToShell
        } else {
            Lin movePtr = new Lin(new Point(x, y), new Point(xnew, ynew), thickness, color);
            movePtr.startPt.h = x;
            movePtr.startPt.v = y;
            movePtr.endPt.h = xnew;
            movePtr.endPt.v = ynew;
            movePtr.color = color;
            lines.add(movePtr);
        }
    }

    /**
     * Pic already contains its own origin, meaning the coordinates at which it
     * was originally drawn. Now draw it at Place
     * 
     * @param g2
     *            the Graphics2D context to draw on.
     * @param place
     *            Where to place the biomorph.
     * @param morph
     *            the biomorph to be drawn.
     */

    public void drawPic(Graphics2D g2) {

        ColourGenome genome = (ColourGenome) morph.getGenome();
        g2.setColor(ColourPic.chooseColor(genome.getBackColorGene().getValue()));
        g2.fillRect(0, 0, margin.getWidth(), margin.getHeight());
        // PenSize(MyPenSize, MyPenSize);
        g2.setStroke(new BasicStroke(1.0f));
        for (Lin line : lines) {

            // sometimes rangecheck error
            actualLine(g2, picStyle, Compass.NorthSouth, line, (ColourBiomorph) morph);
            if (genome.getSpokesGene().getValue() == SpokesType.Radial) {
                if (genome.getCompletenessGene().getValue() == CompletenessType.Single)
                    actualLine(g2, PicStyleType.RUD, Compass.EastWest, (Lin) line, (ColourBiomorph) morph);
                else
                    actualLine(g2, picStyle, Compass.EastWest, (Lin) line, (ColourBiomorph) morph);
            }
        }

        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(Color.BLACK);
    }
}
