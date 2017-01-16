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

	@Override
	public void draw(Graphics2D g2, Point p) {
		g2.setColor(Color.BLACK);
		pic.drawPic(g2);
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
