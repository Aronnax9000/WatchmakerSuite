package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.mutation.Mutagen;
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
	void removePropertyChangeListener(PropertyChangeListener listener);
	/* Factory Methods */

	/**
	 * Creates and returns a new instance of Genome for this morph type.
	 * @return a new Genome of the appropriate type.
	 */
	Genome newGenome();
	Phenotype newPhenotype();
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
	/**
	 * Creates a new Morph with a null Genome.
	 * @return
	 */
	Morph newMorph();

	
	/**
	 * Returns the AppData instance representing a 
	 * running Watchmaker application.
	 * @return the AppData instance associated with this MorphConfig
	 */
	AppData getAppData();
	void setAppData(AppData object);

	boolean isRecordingFossils();
	void setRecordingFossils(boolean newValue);

	Mutagen getMutagen();
    void setMutagen(Mutagen mutagen);


	Embryology getEmbryology();
	Morph reproduce(Morph parentMorph);

	Vector<Morph> getLitter(Morph parentMorph, int litterSize);



}