package net.richarddawkins.watchmaker.genome.gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.gui.MorphView;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public abstract class SimpleGeneBoxStrip extends GeneBoxStrip implements PropertyChangeListener {


	private static final long serialVersionUID = 1L;
	protected MorphConfig config;

	protected Genome genome;

	public SimpleGeneBoxStrip(MorphConfig config, boolean engineeringMode) {
		this.setLayout(new GridLayout(1, config.getGeneBoxCount()));
		this.config = config;
		setEngineeringMode(engineeringMode);
	}

	public Genome getGenome() {
		return genome;
	}

	private void setEngineeringMode(boolean engineeringMode) {
		Component[] components = this.getComponents();
		for (Component component: components) {
			GeneBox geneBox = (GeneBox) component;
			geneBox.setEngineeringMode(engineeringMode);
		}
		repaint();
	}

	public void setGenome(Genome genome) {
		Genome old = this.genome;
		if(old != null) {
			old.removePropertyChangeListener(this);
		}
		this.genome = genome;
		Component[] components = this.getComponents();
		Gene[] genes = genome.toGeneArray();
		for (int i = 0; i < components.length; i++) {
			GeneBox geneBox = (GeneBox) components[i];
			geneBox.setGene(genes[i]);
		}
		genome.addPropertyChangeListener(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		MorphView selectedMorphView = (MorphView) config
				.getMorphViewsTabbedPane()
				.getSelectedComponent();
		selectedMorphView.repaint();
	}
	

	public void setMorphConfig(MorphConfig config) {
		this.config = config;
	}

}
