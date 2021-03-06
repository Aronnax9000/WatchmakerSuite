package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.colour.genome.LimbShapeGene;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbShapeType;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingTextGeneBox;

public class SwingLimbShapeGeneBox extends SwingTextGeneBox {

	private static final long serialVersionUID = 7130996645391902520L;

	public SwingLimbShapeGeneBox(AppData appData) {
	    super(appData);
	}

	public void setLimbShapeType(LimbShapeType limbShapeType) {
		String limbShapeName;
		
		// TODO Add strings for internationalization;
		switch(limbShapeType) {
		case Stick:
			limbShapeName = limbShapeType.toString();
			break;
		case Oval:
			limbShapeName = limbShapeType.toString();
			break;
		case Rectangle:
			limbShapeName = limbShapeType.toString();
			break;
			default:
				limbShapeName = "Unknown";
				// Can't happen;
		}
		valueLabel.setText(limbShapeName);
		repaint();
	}
	
	@Override
	public void setGene(Gene gene) {
		super.setGene(gene);
		setLimbShapeType(((LimbShapeGene) gene).getValue());
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setLimbShapeType((LimbShapeType) evt.getNewValue());
	}

	@Override
	public void setEngineeringMode() {
		super.setEngineeringMode(GeneBoxType.leftRightEquals);
	}

}
