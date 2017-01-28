package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import java.util.logging.Logger;


import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.mutation.Mutagen;

public abstract class SimpleMorphConfig implements MorphConfig {
	@SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morph.SimpleMorphConfig");

	protected AppData appData;	

	protected Embryology embryology;
	
    protected Mutagen mutagen;
	
	
	@Override
	public Vector<Morph> getLitter(Morph parentMorph, int litterSize) {
		Vector<Morph> litter = new Vector<Morph>();
		for(int i = 0; i < litterSize; i++) {
			litter.add(this.reproduce(parentMorph));
		}
		return litter;
	}

	protected boolean recordingFossils;
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}


    public AppData getAppData() {
        return appData;
    }
	public Embryology getEmbryology() {
		return embryology;
	}
	public Mutagen getMutagen() {
		return mutagen;
	}


	@Override
	public boolean isRecordingFossils() {
		return recordingFossils;
	}

	
	protected void wireMorphEvents(Morph morph) {
		morph.addPropertyChangeListener(getEmbryology());
	}

	@Override
	public Morph newMorph(int type) {
		Genome genome = newGenome();
		genome.setBasicType(type);
		Morph morph = newMorph();
		morph.setPhenotype(newPhenotype());
		morph.setGenome(genome);
		wireMorphEvents(morph);
		return morph;
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



	public void setAppData(AppData appData) {
        this.appData = appData;
    }


	public void setEmbryology(Embryology embryology) {
		this.embryology = embryology;
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
	

}
