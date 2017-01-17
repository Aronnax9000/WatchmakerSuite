package net.richarddawkins.watchmaker.genome.gui;

import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.IntegerGene;

public class IntegerGeneBox extends NumericGeneBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 143109406407187838L;

	public IntegerGeneBox(GeneBoxStrip geneBoxStrip, boolean engineeringMode) {
	  super(geneBoxStrip, engineeringMode);
	}
	
	public void setValue(int value){
		valueLabel.setText((value > 0 && ((IntegerGene)gene).isShowPositiveSign() ? "+" : "") + new Integer(value).toString());
		repaint();
	}
	@Override
	public void setGene(Gene gene) {
		super.setGene(gene);
		if(gene != null)
			setValue(((IntegerGene)gene).getValue());
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("value"))
		{
			setValue((Integer) evt.getNewValue());
		}
	}
	


}
