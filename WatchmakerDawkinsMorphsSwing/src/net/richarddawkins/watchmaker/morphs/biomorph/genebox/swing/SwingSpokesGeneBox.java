package net.richarddawkins.watchmaker.morphs.biomorph.genebox.swing;

import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.bio.genebox.SpokesGeneBox;
import net.richarddawkins.watchmaker.morphs.bio.genome.SpokesGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;
import net.richarddawkins.watchmaker.resourceloader.Messages;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingTextGeneBox;

public class SwingSpokesGeneBox extends SwingTextGeneBox implements SpokesGeneBox {

	private static final long serialVersionUID = -1299862384921350925L;

	public SwingSpokesGeneBox() {
		super();
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
		this.setText(labelString);
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

	@Override
	public void setEngineeringMode() {
		super.setEngineeringMode(GeneBoxType.leftRightEquals);
	}


}
