package net.richarddawkins.watchmaker.morphs.biomorph.genome.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.bio.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.resourceloader.Messages;
import net.richarddawkins.watchmaker.swing.genebox.SimpleGeneBox;

public class CompletenessGeneBox extends SimpleGeneBox implements PropertyChangeListener {

	private static final long serialVersionUID = 6490585469193999941L;

	public CompletenessGeneBox(boolean engineeringMode) {
		super(engineeringMode);
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
