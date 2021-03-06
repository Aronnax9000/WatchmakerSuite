package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeChangeListener;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;

/**
 * Morph is the superinterface of all biomorph instances.
 * A biomorph has four basic ingredients:
 * <ul>
 * <li>{@link net.richarddawkins.watchmaker.genome.Genome Genome} - a data structure which determine the makeup of the biomorph, 
 * typically a collection of "genes", numbers inside the computer.</li>
 * <li>Phenotype - a data structure representing the body of the biomorph,
 * such as a collection of line segments or other drawing primitives, represented in
 * a device-independent way.</li>
 * <li>Pedigree - a data structure representing the family tree relationship between
 * the morph and other morphs derived from mutation from it, as well as the parent morph
 * from which it is derived.</li>
 * <li>Image - a device-dependent representation of the biomorph's body, as drawn for
 * a particular graphical user interface. Image is of type java.lang.Object, so an instance
 * of any Java class can be assigned to it.</li>
 * </ul>
 * A biomorph may wish to notify other objects when one of its attributes are updated: for
 * that reason, the Morph interface supports the "bound JavaBeans" design pattern for notifying
 * interested objects of changes to its attributes, via the addPropertyChangeListener() and 
 * removePropertyChangeListener() methods. Methods which wish to force the biomorph to
 * notify its listeners of changes may call the firePropertyChange() method, which sends
 * a PropertyChangeEvent to interested listeners.
 * @author Alan Canon
 *
 */
public interface Morph extends GenomeChangeListener {
    
    /* PropertyChange */
    
	void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
    void firePropertyChange(PropertyChangeEvent event);

    /* Functions */
    
    boolean genomicallyEquals(Morph thatMorph);

	/* Actions */
	
    void kill();
	
	/* Getters */
	
	Embryology getEmbryology();
	Genome getGenome();
	Object getImage();
	Vector<Morph> getMorphAndChildren();
	String getName();
	Pedigree getPedigree();
    Phenotype getPhenotype();
    PhenotypeDrawer getPhenotypeDrawer();
	
	/* Setters */
	
    void setEmbryology(Embryology newValue);
	void setGenome(Genome newValue);
	void setImage(Object newValue);
	void setName(String newValue);
    void setPhenotype(Phenotype newValue);
    void setPhenotypeDrawer(PhenotypeDrawer newValue);
}