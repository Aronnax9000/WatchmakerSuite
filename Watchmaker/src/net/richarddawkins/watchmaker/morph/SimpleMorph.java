package net.richarddawkins.watchmaker.morph;

import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeChangeEvent;
import net.richarddawkins.watchmaker.genome.GenomeChangeListener;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public abstract class SimpleMorph implements Morph, GenomeChangeListener {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morph.SimpleMorph");
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}


    protected Genome genome;

    protected Object image = null;

    protected final MorphPedigree pedigree = new MorphPedigree(this);

    protected Phenotype pic;

    protected Rect pRect;



	@Override
    public Genome getGenome() {
        return genome;
    }
    @Override
    public Object getImage() {
        return image;
    }

    public MorphPedigree getPedigree() {
        return pedigree;
    }

    public Phenotype getPic() {
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
    public void setGenome(Genome newValue)  {
    	Genome oldValue = this.genome;
        genome = newValue;
        genome.addGenomeChangeListener(this);
        pcs.firePropertyChange("genome", null, genome);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public void setPic(Phenotype pic) {
        this.pic = pic;
    }

    public void setPRect(Rect pRect) {
        this.pRect = pRect;
    }
    @Override
    public void genomeChange(GenomeChangeEvent evt) {
    	pcs.firePropertyChange("genome", null, genome);
    }
    
    public void firePropertyChange(PropertyChangeEvent event) {
    	pcs.firePropertyChange(event);
    }
}
