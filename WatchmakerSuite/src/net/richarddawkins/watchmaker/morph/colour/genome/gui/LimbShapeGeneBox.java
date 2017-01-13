package net.richarddawkins.watchmaker.morph.colour.genome.gui;

import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.genome.gui.SimpleGeneBox;
import net.richarddawkins.watchmaker.morph.colour.genome.LimbShapeGene;
import net.richarddawkins.watchmaker.morph.colour.genome.type.LimbShapeType;

public class LimbShapeGeneBox extends SimpleGeneBox {

	private static final long serialVersionUID = 7130996645391902520L;

	public LimbShapeGeneBox(GeneBoxStrip geneBoxStrip, boolean engineeringMode) {
		super(geneBoxStrip, engineeringMode);
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
		setLimbShapeType(((LimbShapeGene) gene).getValue());
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setLimbShapeType((LimbShapeType) evt.getNewValue());
	}

}
