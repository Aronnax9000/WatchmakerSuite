package net.richarddawkins.watchmaker.morphs.concho.swing;

import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.morphs.concho.genome.type.HandednessType;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingGeneBox;

public class SwingHandednessGeneBox extends SwingGeneBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void setEngineeringMode() {
		super.setEngineeringMode(GeneBoxType.leftRightOnly);
		
	}
	
	public void setValue(HandednessType handednessType) {
		if(handednessType == HandednessType.Left)
			setText("Left");
		else if(handednessType == HandednessType.Right)
			setText("Right");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("value")) {
			setValue((HandednessType)evt.getNewValue());
		}
		
	}


}
