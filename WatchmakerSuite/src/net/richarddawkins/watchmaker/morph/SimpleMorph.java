package net.richarddawkins.watchmaker.morph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Pic;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Rect;

public abstract class SimpleMorph implements Morph {
    
    protected MorphConfig config;
    
    public MorphConfig getMorphConfig() {
        return config;
    }

    public void setMorphConfig(MorphConfig config) {
        this.config = config;
    }

    public SimpleMorph(MorphConfig config) {
        this.config = config;
    }
    
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morph.SimpleMorph");

	protected final MorphPedigree pedigree = new MorphPedigree(this);
	public MorphPedigree getPedigree() {
        return pedigree;
    }

    protected Genome genome;
	protected Image image;
    protected Pic pic;
    protected Pic myPic;

    protected Rect pRect;
    
    public Rect getPRect() {
        return pRect;
    }

    public void setPRect(Rect pRect) {
        this.pRect = pRect;
    }

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		getMorphConfig().getSwingPicDrawer().drawPic(g2, pic);
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
		this.pic = pic;
	}

}
