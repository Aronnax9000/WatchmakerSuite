package net.richarddawkins.watchmaker.morph.biomorph.genome.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.genome.gui.SimpleGeneBox;
import net.richarddawkins.watchmaker.morph.biomorph.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.resourceloader.Messages;

public class CompletenessGeneBox extends SimpleGeneBox implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6490585469193999941L;

	public CompletenessGeneBox(GeneBoxStrip geneBoxStrip, boolean engineeringMode) {
		super(geneBoxStrip, engineeringMode);
	}
	
	private void setCompleteness(CompletenessType completeness) {
		switch (completeness) {
		case Single:
			this.valueLabel.setText(Messages.getMessages().getString("STRO_12947,0"));
			break;
		case Double:
			this.valueLabel.setText(Messages.getMessages().getString("STRO_12947,1"));
			break;
		}
		repaint();
	}

	@Override 
	public void setGene(Gene gene) {
        super.setGene(gene);
		setCompleteness(((CompletenessGene) gene).getValue());
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setCompleteness((CompletenessType) evt.getNewValue());
	}


	
	

}
