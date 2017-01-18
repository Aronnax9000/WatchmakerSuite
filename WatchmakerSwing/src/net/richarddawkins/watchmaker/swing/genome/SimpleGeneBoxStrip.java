package net.richarddawkins.watchmaker.swing.genome;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.MorphView;
import net.richarddawkins.watchmaker.swing.SwingAppData;

public abstract class SimpleGeneBoxStrip extends GeneBoxStrip implements PropertyChangeListener {


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
		MorphView selectedMorphView = (MorphView) swingAppData
				.getMorphViewsTabbedPane()
				.getSelectedComponent();
		selectedMorphView.repaint();
	}
	

	public void setMorphConfig(MorphConfig config) {
		this.config = config;
	}

}
