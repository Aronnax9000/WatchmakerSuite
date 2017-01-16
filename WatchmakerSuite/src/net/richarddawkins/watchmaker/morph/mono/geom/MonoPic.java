package net.richarddawkins.watchmaker.morph.mono.geom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Lin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.biomorph.geom.gui.SimpleSwingPic;

public class MonoPic extends SimpleSwingPic {
    public MonoPic(Morph morph) {
        super(morph);
    }

    @Override
    public void picLine(int x, int y, int xnew, int ynew, int thick, Color color) {
        if (thick > 8) {
            thick = 8;
        }
        if (lines.size() >= PICSIZEMAX) {
            // {Message(GetString(TooLargeString));}
            // {used the help dialog! v1.1 changed to alert}
            // DisplayError(-147, 'Biomorph too large, or other problem', '
            // ', StopError);
            // ExitToShell
        } else {
            addPicLines(x, y, xnew, ynew, thick, color);
        }
    }

    public void addPicLines(int x, int y, int xnew, int ynew, int thick, Color color) {
        BiomorphGenome genome = (BiomorphGenome) morph.getGenome();
        addActualPicLine(x, y, xnew, ynew, thick, color, picStyle, Compass.NorthSouth);
        

        if (genome.getSpokesGene().getValue() == SpokesType.Radial) {
            if (genome.getCompletenessGene().getValue() == CompletenessType.Single) {
                addActualPicLine(x, y, xnew, ynew, thick, color, PicStyleType.RUD, Compass.EastWest);
            } else {
                addActualPicLine(x, y, xnew, ynew, thick, color, picStyle, Compass.EastWest);
            }
        }

    }

    private void addActualPicLine(int x, int y, int xnew, int ynew, int thick, Color color, PicStyleType picStyle,
            Compass orientation) {
        int y0;
        int y1;
        int x0;
        int x1;
        if (orientation == Compass.NorthSouth) {
            x0 = x;
            y0 = y;
            x1 = xnew;
            y1 = ynew;
        } else {
            x0 = y;
            y0 = x;
            x1 = ynew;
            y1 = xnew;
        }

        switch (picStyle) {
        case LF:
            addSinglePicLine(new Point(x0, y0), new Point(x1, y1), thick, color);
            break;
        case RF:
            addSinglePicLine(new Point(-x0, y0), new Point(-x1, y1), thick, color);
            break;
        case FF:
            addSinglePicLine(new Point(x0, y0), new Point(x1, y1), thick, color);
            addSinglePicLine(new Point(-x0, y0), new Point(-x1, y1), thick, color);
            break;
        case LUD:
            addSinglePicLine(new Point(x0, y0), new Point(x1, y1), thick, color);
            addSinglePicLine(new Point(-x0, -y0), new Point(-x1, -y1), thick, color);
            break;
        case RUD:
            addSinglePicLine(new Point(-x0, y0), new Point(-x1, y1), thick, color);
            addSinglePicLine(new Point(x0, -y0), new Point(x1, -y1), thick, color);
            break;
        case FUD:
            addSinglePicLine(new Point(x0, y0), new Point(x1, y1), thick, color);
            addSinglePicLine(new Point(-x0, y0), new Point(-x1, y1), thick, color);
            addSinglePicLine(new Point(x0, -y0), new Point(x1, -y1), thick, color);
            addSinglePicLine(new Point(-x0, -y0), new Point(-x1, -y1), thick, color);
            break;
        default:
        }
    }

    private void addSinglePicLine(Point startPt, Point endPt, int thick, Color color) {
        lines.add(new Lin(startPt, endPt, thick, color));
        margin.expandPoint(startPt, thick);
        margin.expandPoint(endPt, thick);
    }

    /**
     * Pic already contains its own origin, meaning the coordinates at which it
     * was originally drawn. Now draw it at Place
     */
    @Override
    public void drawPic(Graphics2D g2) {
        AffineTransform saveTransform = g2.getTransform();
        Point midPoint = margin.getMidPoint();

        g2.translate(-midPoint.h, -midPoint.v);
        for (Lin line : lines) {
            g2.setStroke(new BasicStroke(line.thickness));
            g2.setColor(line.color);
            g2.drawLine(line.startPt.h, line.startPt.v, line.endPt.h, line.endPt.v);
        }
        if (morph.getMorphConfig().isShowBoundingBoxes()) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLUE);
            Rectangle rectangle = margin.toRectangle();
            g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        g2.setTransform(saveTransform);
    }
}
