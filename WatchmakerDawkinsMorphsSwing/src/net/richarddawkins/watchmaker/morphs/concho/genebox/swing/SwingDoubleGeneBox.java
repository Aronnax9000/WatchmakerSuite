package net.richarddawkins.watchmaker.morphs.concho.genebox.swing;

import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.concho.genebox.DoubleGeneBox;
import net.richarddawkins.watchmaker.morphs.concho.genome.DoubleGene;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingTextGeneBox;

public class SwingDoubleGeneBox extends SwingTextGeneBox implements DoubleGeneBox {
	/**
	 * 
	 */
	private static final long serialVersionUID = 143109406407187838L;

	public SwingDoubleGeneBox() {
		
	}
	@Override
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
	@Override
	public void setEngineeringMode() {
		super.setEngineeringMode(GeneBoxType.leftRightOnly);
	}

}
