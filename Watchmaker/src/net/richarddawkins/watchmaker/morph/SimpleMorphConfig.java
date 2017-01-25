package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.mutation.Mutagen;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public abstract class SimpleMorphConfig implements MorphConfig {
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morph.SimpleMorphConfig");

	protected AppData appData;	

	protected Embryology embryology;
	
    protected Mutagen mutagen;
	protected JPanel pageStartPanel;

	
	
	@Override
	public Vector<Morph> getLitter(Morph parentMorph, int litterSize) {
		Vector<Morph> litter = new Vector<Morph>();
		for(int i = 0; i < litterSize; i++) {
			litter.add(this.reproduce(parentMorph));
		}
		return litter;
	}

	@Override
	public Phenotype newPhenotype() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Morph newMorph() {
		// TODO Auto-generated method stub
		return null;
	}



	protected boolean recordingFossils;
	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected final VetoableChangeSupport vcs = new VetoableChangeSupport(this);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}
	
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	@Override
	public void addVetoableChangeListener(VetoableChangeListener listener) {
		this.vcs.addVetoableChangeListener(listener);
	}
	
	@Override
	public void removeVetoableChangeListener(VetoableChangeListener listener) {
		this.vcs.removeVetoableChangeListener(listener);
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
		morph.setPic(newPhenotype());
		try {
			morph.setGenome(genome);
		} catch (PropertyVetoException e) {
			logger.warning("Genome was already set at Morph creation. Genome:" 
					+ morph.getGenome().toString());
		}
		wireMorphEvents(morph);
		return morph;
	}



	@Override
    public Morph reproduce(Morph parentMorph) {
    	Genome childGenome = newGenome();
    	parentMorph.getGenome().copy(childGenome);
    	getMutagen().mutate(childGenome);
    	Morph childMorph = newMorph();
    	try {
			childMorph.setGenome(childGenome);
		} catch (PropertyVetoException e) {
			logger.warning("Genome was already set at Morph creation. Genome:" 
		+ childMorph.getGenome().toString());
		}
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
