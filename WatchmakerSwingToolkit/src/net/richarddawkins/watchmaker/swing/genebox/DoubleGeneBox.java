package net.richarddawkins.watchmaker.swing.genebox;

import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.genome.DoubleGene;
import net.richarddawkins.watchmaker.genome.Gene;

public class DoubleGeneBox extends NumericGeneBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 143109406407187838L;

	public DoubleGeneBox(boolean engineeringMode) {
	  super(engineeringMode);
	}
	public void setValue(Double value){
		valueLabel.setText((value > 0 && ((DoubleGene)gene).isShowPositiveSign() ? "+" : "") + new Double(value).toString());
		repaint();
	}
	@Override
	public void setGene(Gene gene) {
		super.setGene(gene);
		if(gene != null)
			setValue(((DoubleGene)gene).getValue());
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("value"))
		{
			setValue((Double) evt.getNewValue());
		}
		
	}

}
