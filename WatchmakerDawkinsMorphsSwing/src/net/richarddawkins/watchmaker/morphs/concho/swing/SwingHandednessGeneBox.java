package net.richarddawkins.watchmaker.morphs.concho.swing;

import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.concho.genome.HandednessGene;
import net.richarddawkins.watchmaker.morphs.concho.genome.type.HandednessType;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingTextGeneBox;

public class SwingHandednessGeneBox extends SwingTextGeneBox {


	private static final long serialVersionUID = 1L;

	@Override
	public void setEngineeringMode() {
		super.setEngineeringMode(GeneBoxType.leftRightOnly);
		
	}
	
	public void setHandedness(HandednessType handednessType) {
		if(handednessType == HandednessType.Left)
			setText("Left");
		else if(handednessType == HandednessType.Right)
			setText("Right");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setHandedness((HandednessType) evt.getNewValue());
	}
	@Override
	public void setGene(Gene gene) {
	    super.setGene(gene);
	    setHandedness(((HandednessGene) gene).getValue());
	}

}
