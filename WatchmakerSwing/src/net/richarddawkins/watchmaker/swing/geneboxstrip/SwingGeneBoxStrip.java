package net.richarddawkins.watchmaker.swing.geneboxstrip;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.swing.genebox.SwingGeneBox;
import net.richarddawkins.watchmaker.swing.genebox.SwingIntegerGeneBox;

public abstract class SwingGeneBoxStrip extends JPanel implements GeneBoxStrip, PropertyChangeListener {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.geneboxstrip.SwingGeneBoxStrip");

	protected JPanel panel = (JPanel) this;

	protected boolean geneBoxToSide = false;
	
	public boolean isGeneBoxToSide() {
		return geneBoxToSide;
	}

	public void setGeneBoxToSide(boolean geneBoxToSide) {
		this.geneBoxToSide = geneBoxToSide;
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public void setPanel(Object newValue) {
		this.panel = (JPanel) newValue;
	}

	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	protected Genome genome;

	private static final long serialVersionUID = 1L;

	public SwingGeneBoxStrip() {
//		this.setBorder(new LineBorder(Color.GREEN));
	}

	@Override
	public GeneBox getGeneBoxForGene(Gene gene) {
		if (gene instanceof IntegerGene)
			return new SwingIntegerGeneBox();
		else {
			return null;
		}
	}

	public void setGeneBoxCount(int geneBoxCount) {
		this.setLayout(new GridLayout(1, geneBoxCount));

	}

	protected boolean engineeringMode;

	public void setEngineeringMode(boolean newValue) {
		this.engineeringMode = newValue;
	}

	public void setGenome(Genome newGenome) {
		
		this.removeAll();
		if(newGenome != null) {
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.weightx = 1;
			constraints.weighty = 0;
			constraints.fill = GridBagConstraints.HORIZONTAL;
			
			for (Gene gene : newGenome.toGeneArray()) {
				SwingGeneBox geneBox = (SwingGeneBox) getGeneBoxForGene(gene);
				if (engineeringMode) {
					geneBox.setEngineeringMode();
					geneBox.setGene(gene);
				}
				applyGeneSpecificConstraints(constraints, gene);
				add((Component) geneBox, constraints);
				if(this.geneBoxToSide) {
					constraints.gridy++;
				} else {
					constraints.gridx++;
				}
			}
		}
		this.genome = newGenome;
		if(genome != null) {
			Component[] components = this.getComponents();
			Gene[] genes = genome.toGeneArray();
			for (int i = 0; i < components.length; i++) {
				GeneBox geneBox = (GeneBox) components[i];
				geneBox.setGene(genes[i]);
			}
		}
		repaint();
	}
	
	protected void applyGeneSpecificConstraints(GridBagConstraints constraints, Gene gene) {
		
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("genome")) {
			setGenome((Genome) evt.getNewValue());
		}
	}
}
