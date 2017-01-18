package net.richarddawkins.watchmaker.swing.geneboxstrip;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeSupport;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.genebox.GeneBox;

public abstract class SimpleGeneBoxStrip extends GeneBoxStrip {

	PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private static final long serialVersionUID = 1L;
	protected MorphConfig config;

	protected Genome genome;

	public SimpleGeneBoxStrip(SwingAppData swingAppData, boolean engineeringMode) {
		super(swingAppData, engineeringMode);
		this.setLayout(new GridLayout(1, swingAppData.getMorphConfig().getGeneBoxCount()));
	}

	public Genome getGenome() {
		return genome;
	}

	public void setEngineeringMode(boolean engineeringMode) {
		Component[] components = this.getComponents();
		for (Component component: components) {
			GeneBox geneBox = (GeneBox) component;
			geneBox.setEngineeringMode(engineeringMode);
		}
		repaint();
	}

	public void setGenome(Genome newValue) {
		Genome oldValue = this.genome;

		this.genome = newValue;
		Component[] components = this.getComponents();
		Gene[] genes = genome.toGeneArray();
		for (int i = 0; i < components.length; i++) {
			GeneBox geneBox = (GeneBox) components[i];
			geneBox.setGene(genes[i]);
		}
		pcs.firePropertyChange("genome", oldValue, newValue);
	}

	public void setMorphConfig(MorphConfig config) {
		this.config = config;
	}

}
