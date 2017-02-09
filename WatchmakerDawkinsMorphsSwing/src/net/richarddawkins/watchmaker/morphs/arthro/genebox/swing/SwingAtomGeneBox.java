package net.richarddawkins.watchmaker.morphs.arthro.genebox.swing;

import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.arthro.genome.Atom;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingGeneBox;

public class SwingAtomGeneBox extends SwingGeneBox {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.arthro.genome.swing.SwingAtomGeneBox");
	


	public SwingAtomGeneBox() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(null);
		valueLabel.setVerticalAlignment(SwingConstants.TOP);
		valueLabel.setHorizontalAlignment(SwingConstants.LEFT);
//		valueLabel.setBorder(new LineBorder(Color.RED));
		this.add(valueLabel);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Override
	public void setEngineeringMode() {
		super.setEngineeringMode(GeneBoxType.clickForPicker);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setValue((Atom)evt.getNewValue());
		
	}

	private void setValue(Atom newValue) {
		setText(newValue.toString());
	}
	@Override
	public void setGene(Gene gene) {
		super.setGene(gene);
		Atom atom = (Atom) gene;
		setValue(atom);
	}



}
