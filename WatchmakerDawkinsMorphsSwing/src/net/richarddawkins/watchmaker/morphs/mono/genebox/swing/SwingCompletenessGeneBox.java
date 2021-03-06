package net.richarddawkins.watchmaker.morphs.mono.genebox.swing;

import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs. mono.genebox.CompletenessGeneBox;
import net.richarddawkins.watchmaker.morphs.mono.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.resourceloader.Messages;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingTextGeneBox;

public class SwingCompletenessGeneBox extends SwingTextGeneBox implements CompletenessGeneBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingCompletenessGeneBox(AppData appData) {
	    super(appData);
	}
	
	@Override
	public void setEngineeringMode() {
		super.setEngineeringMode(GeneBoxType.leftRightOnly);
	}

	@Override
	public void setGene(Gene gene) {
	    super.setGene(gene);
	    setCompleteness(((CompletenessGene) gene).getValue());
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("value"))
			setCompleteness((CompletenessType) evt.getNewValue());
	}

	@Override
	public void setCompleteness(CompletenessType completenessType) {
			switch (completenessType) {
			case Single:
				setText(Messages.getMessages().getString("STRO_12947,0"));
				break;
			case Double:
				setText(Messages.getMessages().getString("STRO_12947,1"));
				break;
			}
		}
}
