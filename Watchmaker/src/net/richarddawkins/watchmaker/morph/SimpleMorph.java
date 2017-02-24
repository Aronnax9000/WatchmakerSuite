package net.richarddawkins.watchmaker.morph;


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

	@Override
    public boolean genomicallyEquals(Morph thatMorph) {
        
        return genome.genomicallyEquals(thatMorph.getGenome());
    }


    protected String name;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}


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

    protected final Pedigree pedigree = new Pedigree(this);

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

    public Pedigree getPedigree() {
        return pedigree;
    }

    public Phenotype getPhenotype() {
        return pic;
    }

    public Rect getPRect() {
        return pRect;
    }

    @Override
    public void setGenome(Genome newValue)  {
    	Genome oldValue = genome;
        genome = newValue;
        genome.addGenomeChangeListener(this);
        pcs.firePropertyChange("genome", oldValue, newValue);
    }


    public void setImage(Object image) {
        this.image = image;
    }

    public void setPhenotype(Phenotype pic) {
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
