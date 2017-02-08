package net.richarddawkins.watchmaker.swing.geneboxstrip;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.border.StrokeBorder;

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
		this.setLayout(new GridLayout(1, 0));
		this.setBorder(new StrokeBorder(new BasicStroke(1)));
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
		if (genome == null) {
			for (Gene gene : newGenome.toGeneArray()) {
				SwingGeneBox geneBox = (SwingGeneBox) getGeneBoxForGene(gene);
				if (engineeringMode)
					geneBox.setEngineeringMode();
				add((Component) geneBox);
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
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("genome")) {
			setGenome((Genome) evt.getNewValue());
		}
	}
}
