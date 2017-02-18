package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeFactory;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.genome.mutation.Mutagen;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

/**
 * MorphConfig provides access to the objects which support the
 * creation, reproduction, mutation, development of a particular
 * kind of Morph, independent of implementation-specific graphics
 * drawing paradigm. Access to the application as a whole, and
 * its graphical user interface set, is maintained through MorphConfig's
 * association with an instance of the AppData class. 
 * @author Alan Canon
 *
 */
public interface MorphConfig {

	void addPropertyChangeListener(PropertyChangeListener listener);
	AllowedMutations getAllowedMutations();
	
	/**
	 * Returns the AppData instance representing a 
	 * running Watchmaker application.
	 * @return the AppData instance associated with this MorphConfig
	 */
	AppData getAppData();
	Embryology getEmbryology();

	GenomeFactory getGenomeFactory();
	/**
	 * Generates a new litter and returns the eldest sibling among them.
	 * @param parentMorph the parent morph to breed from
	 * @param litterSize how many offspring to return
	 * @return the eldest sibling among the litter of new offspring.
	 */
	Morph getLitter(Morph parentMorph, int litterSize);
	Mutagen getMutagen();
	int getStartingMorphBasicType();

	
	boolean isRecordingFossils();
	/**
	 * Creates and returns a new instance of Genome for this morph type.
	 * @return a new Genome of the appropriate type.
	 */
	Genome newGenome();

	/**
	 * Creates a new Morph with a null Genome.
	 * @return a Morph with a null Genome.
	 */
	Morph newMorph();
	/**
	 * Create a new Genome initialized to a specified one of a number of
	 * built-in Genomes.
	 * 
	 * This method may throw an unchecked exception if the parameter is out of range.
	 * @param type An integer representing one of the built-in Genomes for this type
	 * of Morph.
	 * @return a Morph with a Genome initialized to the specified type.
	 */
	Morph newMorph(int type);

	Phenotype newPhenotype();
    void removePropertyChangeListener(PropertyChangeListener listener);
	/* Factory Methods */


	Morph reproduce(Morph parentMorph);
	void setAllowedMutations(AllowedMutations muts);

	void setAppData(AppData object);
	void setEmbryology(Embryology embryology);
	void setGenomeFactory(GenomeFactory factory);
	void setMutagen(Mutagen mutagen);
	void setRecordingFossils(boolean newValue);
	void setStartingMorphBasicType(int startingMorphBasicType);
	Vector<BoxedMorphCollection> getAlbums();
	String[] getSavedAnimals();


}