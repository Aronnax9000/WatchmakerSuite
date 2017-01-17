package net.richarddawkins.watchmaker.morph.mono.geom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Lin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.biomorph.geom.gui.SimpleSwingPic;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbShapeType;
import net.richarddawkins.watchmaker.morph.colour.geom.ColourLin;

public class MonoPic extends SimpleSwingPic {
    public MonoPic(Morph morph) {
        super(morph);
    }

    
    public void picLine(int x, int y, int xnew, int ynew, int thick) {
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
            Lin lin = new Lin(new Point(x,y), new Point(xnew,ynew), thick);
            
            addPicLines(lin);
        }
    }

    public void addPicLines(Lin lin) {
        addActualPicLine(lin.clone(), picStyle, Compass.NorthSouth);

        BiomorphGenome genome = (BiomorphGenome) morph.getGenome();
        if (genome.getSpokesGene().getValue() == SpokesType.Radial) {
            if (genome.getCompletenessGene().getValue() == CompletenessType.Single) {
                addActualPicLine(lin.clone(), PicStyleType.RUD, Compass.EastWest);
            } else {
                addActualPicLine(lin.clone(), picStyle, Compass.EastWest);
            }
        }

    }

    private void addActualPicLine(Lin lin, 
            PicStyleType picStyle,
            Compass orientation) {
        int y0;
        int y1;
        int x0;
        int x1;
        if (orientation == Compass.NorthSouth) {
            x0 = lin.startPt.h;
            y0 = lin.startPt.v;
            x1 = lin.endPt.h;
            y1 = lin.endPt.v;
        } else {
            x0 = lin.startPt.v;
            y0 = lin.startPt.h;
            x1 = lin.endPt.v;
            y1 = lin.endPt.h;
        }

        switch (picStyle) {
        case LF:
            addSinglePicLine(new Point(x0, y0), new Point(x1, y1), lin);
            break;
        case RF:
            addSinglePicLine(new Point(-x0, y0), new Point(-x1, y1), lin);
            break;
        case FF:
            addSinglePicLine(new Point(x0, y0), new Point(x1, y1), lin);
            addSinglePicLine(new Point(-x0, y0), new Point(-x1, y1), lin);
            break;
        case LUD:
            addSinglePicLine(new Point(x0, y0), new Point(x1, y1), lin);
            addSinglePicLine(new Point(-x0, -y0), new Point(-x1, -y1), lin);
            break;
        case RUD:
            addSinglePicLine(new Point(-x0, y0), new Point(-x1, y1), lin);
            addSinglePicLine(new Point(x0, -y0), new Point(x1, -y1), lin);
            break;
        case FUD:
            addSinglePicLine(new Point(x0, y0), new Point(x1, y1), lin);
            addSinglePicLine(new Point(-x0, y0), new Point(-x1, y1), lin);
            addSinglePicLine(new Point(x0, -y0), new Point(x1, -y1), lin);
            addSinglePicLine(new Point(-x0, -y0), new Point(-x1, -y1), lin);
            break;
        default:
        }
    }

    private void addSinglePicLine(Point startPt, Point endPt, Lin linPrototype) {
        Lin lin = linPrototype.clone();
        lin.startPt = startPt;
        lin.endPt = endPt;
        addLin(lin);
        lin.expandMargin(margin);
        
    }

    public BufferedImage toImage() {
        BufferedImage image = new BufferedImage(margin.getWidth(), margin.getHeight(), 
                BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D g2 = image.createGraphics();
        Point midPoint = margin.getMidPoint();
        g2.translate(margin.left, margin.bottom);
        for (Lin line : lines) {

            g2.setStroke(new BasicStroke(line.thickness));
            
                
            limb(g2, line);
        }
        if (morph.getMorphConfig().isShowBoundingBoxes()) {
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLUE);
            Rectangle rectangle = margin.toRectangle();
            g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }

        return image;
    }

    protected void limb(Graphics2D g2, Lin line) {            
        
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

    /**
     * Pic already contains its own origin, meaning the coordinates at which it
     * was originally drawn. Now draw it at Place
     */
    @Override
    public void drawPic(Graphics2D g2) {
        AffineTransform saveTransform = g2.getTransform();
        Point midPoint = margin.getMidPoint();
        g2.drawImage(toImage(), -margin.getWidth() / 2, -margin.getHeight() / 2, null);
        g2.setTransform(saveTransform);
    }
}
