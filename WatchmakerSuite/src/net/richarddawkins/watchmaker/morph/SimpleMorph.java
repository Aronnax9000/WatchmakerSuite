package net.richarddawkins.watchmaker.morph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Pic;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Rect;
import net.richarddawkins.watchmaker.morph.biomorph.geom.gui.SimpleSwingPic;

public abstract class SimpleMorph implements Morph {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morph.SimpleMorph");

	protected final MorphPedigree pedigree = new MorphPedigree(this);
	public MorphPedigree getPedigree() {
        return pedigree;
    }

    protected Genome genome;
	protected Image image;
    protected SimpleSwingPic pic;
    protected SimpleSwingPic myPic;

    protected Rect pRect;
    
    public Rect getPRect() {
        return pRect;
    }

    public void setPRect(Rect pRect) {
        this.pRect = pRect;
    }

    public void develop(Point here, boolean zeroMargin) {
        if(zeroMargin) {
            pic.margin.left = here.h;
            pic.margin.right = here.h;
            pic.margin.top = here.v;
            pic.margin.bottom = here.v;
        }
        Point centre = new Point(here.h, here.v);
    }
    
    private void drawPic(Pic myPic2, Point offCentre, Genome genome2) {
        
        
    }
    
    /**
     * <pre>
     * procedure Delayvelop(var Biomorph: Person; Here: Point);
     * var
     *   margcentre, offset: integer;
     *   OffCentre: Point;
     * begin
     *   develop(Biomorph, Here, true);
     *   with margin do
     *     margcentre := top + (bottom - top) div 2;
     *   offset := margcentre - Here.v;
     *   with Margin do
     *   begin
     *     Top := Top - Offset;
     *     Bottom := Bottom - Offset;
     *   end;
     *   with OffCentre do
     *   begin
     *     h := Here.h;
     *     v := Here.v - offset;
     *   end;
     *   DrawPic(MyPic, offcentre, Biomorph);
     * end; {Delayvelop}      
     */
    public void delayvelop(Point here) {
        develop(here, true);
        int margCentre = pic.margin.top + (pic.margin.bottom - pic.margin.top) / 2;
        int offset = margCentre - here.v;
        pic.margin.top = pic.margin.top - offset;
        pic.margin.bottom = pic.margin.bottom - offset;
        Point offCentre = new Point(here.h, here.v - offset);
        drawPic(myPic, offCentre, genome);
    }


    public void delayvelop(Graphics2D g2, Point p, boolean midBox) {
		int margcentre, offset;
		Point offCentre = new Point();
		// Zeromargin := TRUE;

		getGenome().develop(null, p, true); // null equivalent to classic
											// DelayedDrawing := TRUE;
		// DelayedDrawing := FALSE;
		margcentre = pic.margin.top + (pic.margin.bottom - pic.margin.top) / 2;
		offset = margcentre - p.v;
		pic.margin.top -= offset;
		pic.margin.bottom -= offset;
		offCentre.h = p.h;
		offCentre.v = p.v - offset;
		pic.drawPic(g2, p, offCentre, this, pic.getPicStyleType());
		if (this.getMorphConfig().isShowBoundingBoxes()) {
			g2.setColor(Color.RED);
			Rectangle rectangle = pic.margin.toRectangle();
			g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
	}

	@Override
	public void draw(Graphics2D g2, Point p, boolean midBox) {
		g2.setColor(Color.BLACK);
		delayvelop(g2, p, midBox);
	}

	@Override
	public Genome getGenome() {
		return genome;
	}

	public Image getImage() {
		return image;
	}

	public Pic getPic() {
		return pic;
	}

	@Override
	public void setGenome(Genome genome) {
		this.genome = genome;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setPic(Pic pic) {
		this.pic = (SimpleSwingPic)pic;
	}

}
