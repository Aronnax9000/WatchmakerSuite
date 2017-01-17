package net.richarddawkins.watchmaker.morphs.bio.geom;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.bio.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;

public abstract class BiomorphPic extends Pic {
    
    
    public BiomorphPic(Morph morph) {
        super(morph);
        calculatePicStyleType();
    }
    
    protected PicStyleType picStyle;
    
    public void calculatePicStyleType() {
        BiomorphGenome genome = (BiomorphGenome) morph.getGenome();
        picStyle = PicStyleType.FF;
        switch (genome.getCompletenessGene().getValue()) {
        case Single: {
            switch (genome.getSpokesGene().getValue()) {
            case NorthOnly:
                picStyle = PicStyleType.LF;
                break;
            case NSouth:
                picStyle = PicStyleType.LUD;
                break;
            case Radial:
                picStyle = PicStyleType.LUD;
                break;
            }
            break;
        }
        case Double:
            switch (genome.getSpokesGene().getValue()) {
            case NorthOnly: {
                picStyle = PicStyleType.FF;
                break;
            }
            case NSouth: {
                picStyle = PicStyleType.FUD;
                break;
            }
            case Radial: {
                picStyle = PicStyleType.FUD;
                break;
            }
            }
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

}
