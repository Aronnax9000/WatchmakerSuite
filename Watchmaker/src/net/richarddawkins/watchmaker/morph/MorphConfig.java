package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.mutation.Mutagen;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public interface MorphConfig {
    

	int getGeneBoxCount();
	
	Object getAppData();
	void setAppData(Object object);

	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);

	boolean isRecordingFossils();

	void setRecordingFossils(boolean newValue);

    void setMutagen(Mutagen mutagen);
	Mutagen getMutagen();

	Genome newGenome();
	Phenotype newPhenotype();
	int getDefaultBreedingRows();

	int getDefaultBreedingCols();

	void setDefaultBreedingRows(int defaultBreedingRows);

	void setDefaultBreedingCols(int defaultBreedingCols);

	Embryology getEmbryology();

	Morph newMorph(int type);
	Morph newMorph();


}