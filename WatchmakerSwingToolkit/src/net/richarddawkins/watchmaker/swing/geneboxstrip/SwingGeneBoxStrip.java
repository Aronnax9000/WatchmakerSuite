package net.richarddawkins.watchmaker.swing.geneboxstrip;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeSupport;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.swing.genebox.SwingGeneBox;
import net.richarddawkins.watchmaker.swing.genebox.SwingIntegerGeneBox;

public abstract class SwingGeneBoxStrip extends JPanel implements GeneBoxStrip {

	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	protected Genome genome;
	
	private static final long serialVersionUID = 1L;

	
	public SwingGeneBoxStrip() { }
	@Override
	public GeneBox getGeneBoxForGene(Gene gene) {
		if(gene instanceof IntegerGene)
			return new SwingIntegerGeneBox();
		else
			return null;
	}
	
	public void setGeneBoxCount(int geneBoxCount) {
		this.setLayout(new GridLayout(1, geneBoxCount));
		
	}

	public void setEngineeringMode(boolean engineeringMode) {
		Component[] components = this.getComponents();
		for (Component component: components) {
			GeneBox geneBox = (GeneBox) component;
			if(engineeringMode)
				geneBox.setEngineeringMode();
		}
	}

	public void setGenome(Genome newGenome) {
		if(genome == null) {
			for(Gene gene: genome.toGeneArray()) {
					add((SwingGeneBox)getGeneBoxForGene(gene));
			}
		}
		this.genome = newGenome;
		Component[] components = this.getComponents();
		Gene[] genes = genome.toGeneArray();
		for (int i = 0; i < components.length; i++) {
			GeneBox geneBox = (GeneBox) components[i];
			geneBox.setGene(genes[i]);
		}
	}
}
