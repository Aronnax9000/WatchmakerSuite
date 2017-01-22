package net.richarddawkins.watchmaker.morphs.bio.geom;

import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morphs.bio.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;

public abstract class BiomorphPic extends Pic {
    
    
    public enum Compass {
        EastWest, NorthSouth
    }

    public enum PicStyleType {
        FF, FSW, FUD, LF, LSW, LUD, RF, RSW, RUD
    }

	public PicStyleType picStyle = PicStyleType.FF;
    
    public void addPicLines(BiomorphGenome genome, Lin lin) {
        addActualPicLine(lin.clone(), picStyle, Compass.NorthSouth);
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

}
