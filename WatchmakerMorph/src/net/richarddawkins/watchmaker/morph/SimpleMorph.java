package net.richarddawkins.watchmaker.morph;

import java.awt.Image;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.geom.Pic;
import net.richarddawkins.watchmaker.geom.Rect;

public abstract class SimpleMorph implements Morph {

    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morph.SimpleMorph");

    protected MorphConfig config;

    protected Genome genome;

    protected Object image;

    protected Pic myPic;

    protected final MorphPedigree pedigree = new MorphPedigree(this);

    protected Pic pic;

    protected Rect pRect;

    public SimpleMorph(MorphConfig config) {
        this.config = config;
    }

    @Override
    public Genome getGenome() {
        return genome;
    }
    @Override
    public Object getImage() {
        return image;
    }
    public MorphConfig getMorphConfig() {
        return config;
    }

    public MorphPedigree getPedigree() {
        return pedigree;
    }

    public Pic getPic() {
        return pic;
    }

    public Rect getPRect() {
        return pRect;
    }

    // @Override
    // public void draw(Graphics2D g2) {
    // g2.setColor(Color.BLACK);
    // getMorphConfig().getSwingPicDrawer().drawPic(g2, pic);
    // }

    @Override
    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public void setMorphConfig(MorphConfig config) {
        this.config = config;
    }

    public void setPic(Pic pic) {
        this.pic = pic;
    }

    public void setPRect(Rect pRect) {
        this.pRect = pRect;
    }

}
