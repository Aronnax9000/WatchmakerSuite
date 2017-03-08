package net.richarddawkins.watchmaker.swing.genebox;

import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genebox.IntegerGeneBox;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.IntegerGene;

public class SwingIntegerGeneBox extends SwingTextGeneBox implements IntegerGeneBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 143109406407187838L;

	protected boolean displayAsHex = false;
	
	public SwingIntegerGeneBox(AppData appData) {
	    super(appData);
	}

	public void setEngineeringMode() {
		super.setEngineeringMode(GeneBoxType.leftRightOnly);
	}

	public void setValue(int value) {
	    String valueString;
	    if(displayAsHex) {
	        valueString = Integer.toHexString(value).toUpperCase();
	    } else {
	        valueString = new Integer(value).toString();
	    }
		setText((value > 0 && ((IntegerGene) gene).isShowPositiveSign() ? "+" : "") + valueString);
	}

	@Override
	public void setGene(Gene gene) {
		super.setGene(gene);
		if (gene != null)
			setValue(((IntegerGene) gene).getValue());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("value")) {
			setValue((Integer) evt.getNewValue());
		}
	}

}
