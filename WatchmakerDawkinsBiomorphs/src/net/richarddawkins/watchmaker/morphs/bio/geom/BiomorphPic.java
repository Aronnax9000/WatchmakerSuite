package net.richarddawkins.watchmaker.morphs.bio.geom;

import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;

public abstract class BiomorphPic extends Pic {
    
    public enum Compass {
        EastWest, NorthSouth
    }
	
    public enum PicStyleType {
        FF, FSW, FUD, LF, LSW, LUD, RF, RSW, RUD
    }

    protected CompletenessType completenessType;

	public PicStyleType picStyle = PicStyleType.FF;
    
	protected SpokesType spokesType;
	

	public void addLin(Lin lin) {
        addActualPicLine(lin.clone(), picStyle, Compass.NorthSouth);
        if (spokesType == SpokesType.Radial) {
            if (completenessType == CompletenessType.Single) {
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
        super.addLin(lin);
        lin.expandMargin(margin);
        
    }

	public int getBackgroundColor() { return -1; }

	public CompletenessType getCompletenessType() {
		return completenessType;
	}

	public PicStyleType getPicStyle() {
		return picStyle;
	}

	public SpokesType getSpokesType() {
		return spokesType;
	}
	
    public void setCompletenessType(CompletenessType completenessType) {
		this.completenessType = completenessType;
	}

    public void setPicStyle(PicStyleType picStyle) {
		this.picStyle = picStyle;
	}

    public void setSpokesType(SpokesType spokesType) {
		this.spokesType = spokesType;
	}

}
