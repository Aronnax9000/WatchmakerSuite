package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeFactory;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.genome.mutation.Mutagen;

public abstract class SimpleMorphConfig implements MorphConfig {
	@SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morph.SimpleMorphConfig");
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	protected AllowedMutations allowedMutations;	
	protected AppData appData;
	protected Embryology embryology;
	protected GenomeFactory genomeFactory;
	protected Mutagen mutagen;
    protected boolean recordingFossils;

	int startingMorphBasicType;

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	@Override
	public AllowedMutations getAllowedMutations() {
		return allowedMutations;
	}
	@Override
	public void setAllowedMutations(AllowedMutations muts) {
		this.allowedMutations = muts;
	}
	public AppData getAppData() {
        return appData;
    }

	public Embryology getEmbryology() {
		return embryology;
	}

	public GenomeFactory getGenomeFactory() {
		return genomeFactory;
	}

	@Override
	public Morph getLitter(Morph parentMorph, int litterSize) {
		Vector<Morph> litter = new Vector<Morph>();
		for(int i = 0; i < litterSize; i++) {
			litter.add(this.reproduce(parentMorph));
		}
		return litter.elementAt(0);
	}
	public Mutagen getMutagen() {
		return mutagen;
	}

	public int getStartingMorphBasicType() {
		return startingMorphBasicType;
	}
	
	@Override
	public boolean isRecordingFossils() {
		return recordingFossils;
	}


    @Override
	public Morph newMorph(int type) {
		Genome genome = genomeFactory.getBasicType(type);
		Morph morph = newMorph();
		morph.setPhenotype(newPhenotype());
		morph.setGenome(genome);
		wireMorphEvents(morph);
		return morph;
	}
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}
	@Override
    public Morph reproduce(Morph parentMorph) {
    	Genome childGenome = newGenome();
    	parentMorph.getGenome().copy(childGenome);
    	getMutagen().mutate(childGenome);
    	Morph childMorph = newMorph();
		childMorph.setGenome(childGenome);
    	parentMorph.getPedigree().addOffspring(childMorph);
    	return childMorph;
    }


	@Override
	public void setAppData(AppData appData) {
        this.appData = appData;
    }
	@Override
	public void setEmbryology(Embryology embryology) {
		this.embryology = embryology;
	}



	public void setGenomeFactory(GenomeFactory genomeFactory) {
		this.genomeFactory = genomeFactory;
	}



	public void setMutagen(Mutagen mutagen) {
		this.mutagen = mutagen;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.impl.MorphConfig#
	 * setRecordingFossils(boolean)
	 */
	@Override
	public void setRecordingFossils(boolean newValue) {
		boolean oldValue = recordingFossils;
		recordingFossils = newValue;
		if (newValue != oldValue)
			pcs.firePropertyChange("recordingFossils", oldValue, newValue);
	}
	@Override
	public void setStartingMorphBasicType(int startingMorphBasicType) {
		this.startingMorphBasicType = startingMorphBasicType;
	}

	protected void wireMorphEvents(Morph morph) {
		morph.addPropertyChangeListener(getEmbryology());
	}
	

}
