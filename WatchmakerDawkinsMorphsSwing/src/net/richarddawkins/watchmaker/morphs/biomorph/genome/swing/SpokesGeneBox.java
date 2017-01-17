package net.richarddawkins.watchmaker.morphs.biomorph.genome.swing;

import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.genome.gui.SimpleGeneBox;
import net.richarddawkins.watchmaker.morphs.bio.genome.SpokesGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;
import net.richarddawkins.watchmaker.resourceloader.Messages;

public class SpokesGeneBox extends SimpleGeneBox {

	private static final long serialVersionUID = -1299862384921350925L;

	public SpokesGeneBox(GeneBoxStrip geneBoxStrip, boolean engineeringMode) {
		super(geneBoxStrip, engineeringMode);
	}
	
	private void setSpokes(SpokesType spokes) {
		String labelString;
		switch (spokes) {
		case NorthOnly:
			labelString = Messages.getMessages().getString("STRO_12947,2");
			break;
		case NSouth:
			labelString = Messages.getMessages().getString("STRO_12947,3");
			break;
		case Radial:
			labelString = Messages.getMessages().getString("STRO_12947,4");
			break;
		default:
			labelString = ""; // Shouldn't happen.
		}
		this.valueLabel.setText(labelString);
		repaint();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setSpokes((SpokesType) evt.getNewValue());
	}
	@Override
	public void setGene(Gene gene) {
	    super.setGene(gene);
		setSpokes(((SpokesGene) gene).getValue());
	}


}
