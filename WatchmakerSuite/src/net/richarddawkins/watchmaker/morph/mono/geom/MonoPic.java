package net.richarddawkins.watchmaker.morph.mono.geom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Lin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.biomorph.geom.gui.SimpleSwingPic;
import net.richarddawkins.watchmaker.morph.mono.genome.MonochromeGenome;
import net.richarddawkins.watchmaker.morph.util.Globals;

public class MonoPic extends SimpleSwingPic {
    public MonoPic(Morph morph) {
        super(morph);
    }

    public void picLine(int x, int y, int xnew, int ynew, int thick, Color color, PicStyleType picStyle) {
        if (thick > 8) {
            thick = 8;
        }
        if (picSize >= PICSIZEMAX) {
            // {Message(GetString(TooLargeString));}
            // {used the help dialog! v1.1 changed to alert}
            // DisplayError(-147, 'Biomorph too large, or other problem', '
            // ', StopError);
            // ExitToShell
        } else {
            picLine(x, y, xnew, ynew, thick, color);
        }
    }

    @Override
    public void picLine(int x, int y, int xnew, int ynew, int thick, Color color) {

        Lin movePtr = new Lin();
        movePtr.startPt.h = x;
        movePtr.startPt.v = y;
        movePtr.endPt.h = xnew;
        movePtr.endPt.v = ynew;
        movePtr.thickness = thick;
        lines.add(movePtr);
        picSize++;
        doExpansion(x, y, xnew, ynew, thick);
    }
    
    public void doExpansion(int x, int y, int xnew, int ynew, int thick) {
        MonochromeGenome monoGenome = (MonochromeGenome) morph.getGenome();
        CompletenessType completenessType = monoGenome.getCompletenessGene().getValue();
        SpokesType spokesType = monoGenome.getSpokesGene().getValue();

        margin.expandPoint(new Point(x, y), thick);
        margin.expandPoint(new Point(xnew, ynew), thick);

        if (completenessType == CompletenessType.Double || spokesType == SpokesType.NSouth
                || spokesType == SpokesType.Radial) {
            margin.expandHorizontal(-x, thick);
            margin.expandHorizontal(-xnew, thick);
        }

        switch (spokesType) {
        case NorthOnly:
            break;
        case Radial:
            margin.expandHorizontal(-y, thick);
            margin.expandHorizontal(-ynew, thick);
            margin.expandVertical(-x, thick);
            margin.expandVertical(-xnew, thick);
        case NSouth:
            margin.expandVertical(-y, thick);
            margin.expandVertical(-ynew, thick);
            break;
        default:

        }
    }

    /**
     * Pic already contains its own origin, meaning the coordinates at which it
     * was originally drawn. Now draw it at Place
     */
    @Override
    public void drawPic(Graphics2D g2, Point d, Point place, Morph morph, PicStyleType picStyle) {
        MonochromeGenome genome = (MonochromeGenome) morph.getGenome();


        g2.setStroke(new BasicStroke(Globals.myPenSize));
        for (Lin line : lines) {
            actualLine(g2, line, place, picStyle, Compass.NorthSouth);
            // sometimes rangecheck error

            if (genome.getSpokesGene().getValue() == SpokesType.Radial) {
                if (genome.getCompletenessGene().getValue() == CompletenessType.Single) {
                    actualLine(g2, line, place, PicStyleType.RUD, Compass.EastWest);
                } else {
                    actualLine(g2, line, place, picStyle, Compass.EastWest);
                }
            }
        }
        g2.setStroke(new BasicStroke(1.0f));
        // PenSize(1, 1);
    }

    void actualLine(Graphics2D g2, Lin line, Point place, PicStyleType picStyle, Compass orientation) {

        int y0;
        int y1;
        int x0;
        int x1;

        g2.setStroke(new BasicStroke(line.thickness));
        if (orientation == Compass.NorthSouth) {
            int horizOffset = origin.h - place.h;
            int vertOffset = origin.v - place.v;
            x0 = line.startPt.h - horizOffset;
            y0 = line.startPt.v - vertOffset;
            x1 = line.endPt.h - horizOffset;
            y1 = line.endPt.v - vertOffset;
        } else {
            int horizOffset = origin.v - place.h;
            int vertOffset = origin.h - place.v;
            x0 = line.startPt.v - horizOffset;
            y0 = line.startPt.h - vertOffset;
            x1 = line.endPt.v - horizOffset;
            y1 = line.endPt.h - vertOffset;
        }

        int mid2 = 2 * place.h;
        int belly2 = 2 * place.v;

        switch (picStyle) {
        case LF:
            g2.drawLine(x0, y0, x1, y1);
            break;
        case RF:
            g2.drawLine(mid2 - x0, y0, mid2 - x1, y1);
            break;
        case FF:
            g2.drawLine(x0, y0, x1, y1);
            g2.drawLine(mid2 - x0, y0, mid2 - x1, y1);
            break;
        case LUD:
            g2.drawLine(x0, y0, x1, y1);
            g2.drawLine(mid2 - x0, belly2 - y0, mid2 - x1, belly2 - y1);
            break;
        case RUD:
            g2.drawLine(mid2 - x0, y0, mid2 - x1, y1);
            g2.drawLine(x0, belly2 - y0, x1, belly2 - y1);
            break;
        case FUD:
            g2.drawLine(x0, y0, x1, y1);
            g2.drawLine(mid2 - x0, y0, mid2 - x1, y1);
            g2.drawLine(x0, belly2 - y0, x1, belly2 - y1);
            g2.drawLine(mid2 - x0, belly2 - y0, mid2 - x1, belly2 - y1);
            break;
        default:
        }
    }

    
    
}
