package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeChangeEvent;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;

public abstract class SimpleMorph implements Morph {


    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.morph.SimpleMorph");
    protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    protected PhenotypeDrawer phenotypeDrawer;
    protected Embryology embryology;
    public PhenotypeDrawer getPhenotypeDrawer() {
        return phenotypeDrawer;
    }

    public void setPhenotypeDrawer(PhenotypeDrawer phenotypeDrawer) {
        this.phenotypeDrawer = phenotypeDrawer;
    }

    public Embryology getEmbryology() {
        return embryology;
    }

    public void setEmbryology(Embryology embryology) {
        this.embryology = embryology;
    }

    protected Genome genome;
    protected Object image = null;
    protected String name;
    protected Pedigree pedigree = new Pedigree(this);
    protected Phenotype pic;

    @Override
    public void kill() {
        image = null;
        Vector<Pedigree> closet = new Vector<Pedigree>();

        for (Morph morph : this.getMorphAndChildren()) {
            closet.add(morph.getPedigree());
        }
        ;
        for (Pedigree skeleton : closet) {
            skeleton.kill();
        }

        // pedigree = null;

        genome.kill();
        genome = null;

        pic.zero();
        pic = null;

    }

    @Override
    public void genomeChange(GenomeChangeEvent evt) {
        logger.info("SimpleMorph.genomeChange " + evt);
 
        if(embryology != null) {
            embryology.develop(this);
            pcs.firePropertyChange("phenotype", null, this.getPhenotype());
        }
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
            Pedigree childPedigree = child.getPedigree();
            if (childPedigree != null) {
                child = childPedigree.youngerSib;
            } else {
                break;
            }
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

    @Override
    public void setGenome(Genome newValue) {
        logger.info("Morph.setGenome");
        Genome oldValue = this.genome;
        if (oldValue != null) {
            oldValue.removeGenomeChangeListener(this);
        }
        this.genome = newValue;
        if (newValue != null) {
            newValue.addGenomeChangeListener(this);
            if(embryology != null) {
                embryology.develop(this);
            }
        }
        
        pcs.firePropertyChange("genome", oldValue, newValue);
    }

    @Override
    public void setImage(Object newValue) {
        Object oldValue = this.image;
        this.image = newValue;
        pcs.firePropertyChange("image", oldValue, newValue);
    }

    @Override
    public void setName(String newValue) {
        String oldValue = this.name;
        this.name = newValue;
        pcs.firePropertyChange("name", oldValue, newValue);
    }

    @Override
    public void setPhenotype(Phenotype newValue) {
        Phenotype oldValue = this.pic;
        this.pic = newValue;
        if(newValue != null) {
            if(phenotypeDrawer != null) {
                Object image = phenotypeDrawer.getImage(newValue);
                setImage(image);
            }
        }
        pcs.firePropertyChange("phenotype", oldValue, newValue);
    }

    @Override
    public String toString() {
        return "Morph Phenotype:" + this.getPhenotype() + "Genome:"
                + this.getGenome();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void firePropertyChange(PropertyChangeEvent event) {
        logger.info("Morph firing property change event for property: "
                + event.getPropertyName() + " newValue " + event.getNewValue());
        pcs.firePropertyChange(event);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
    

}
