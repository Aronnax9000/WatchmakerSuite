package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeChangeEvent;
import net.richarddawkins.watchmaker.genome.GenomeChangeListener;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public abstract class SimpleMorph implements Morph, GenomeChangeListener {

    @SuppressWarnings("unused")
    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.morph.SimpleMorph");
    protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    protected Genome genome;
    protected Object image = null;
    protected String name;
    protected Pedigree pedigree = new Pedigree(this);
    protected Phenotype pic;
    protected Rect pRect;

    @Override
    public void genomeChange(GenomeChangeEvent evt) {
        pcs.firePropertyChange("genome", null, genome);
    }

    @Override
    public boolean genomicallyEquals(Morph thatMorph) {
        return genome.genomicallyEquals(thatMorph.getGenome());
    }

    @Override
    public Genome getGenome() {
        return genome;
    }

    @Override
    public Object getImage() {
        return image;
    }

    @Override
    public Vector<Morph> getMorphAndChildren() {
        Vector<Morph> morphs = new Vector<Morph>();
        morphs.add(this);
        Morph child = this.getPedigree().firstBorn;
        while (child != null) {
            morphs.addElement(child);
            child = child.getPedigree().youngerSib;
        }
        return morphs;

    }

    @Override
    public String getName() {
        return name;
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
    public void setGenome(Genome newValue) {
        Genome oldValue = genome;
        genome = newValue;
        genome.addGenomeChangeListener(this);
        pcs.firePropertyChange("genome", oldValue, newValue);
    }

    public void setImage(Object image) {
        this.image = image;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setPhenotype(Phenotype pic) {
        this.pic = pic;
    }

    public void setPRect(Rect pRect) {
        this.pRect = pRect;
    }

    
    @Override
    public String toString() {
        return "Morph Phenotype:" + this.getPhenotype().toString() + "Genome:" + this.getGenome().toString();
    }
    
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void firePropertyChange(PropertyChangeEvent event) {
        pcs.firePropertyChange(event);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

}
