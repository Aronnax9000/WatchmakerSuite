package net.richarddawkins.watchmaker.morphs.arthro.genome.swing;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.arthro.genome.Atom;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingGeneBox;

public class SwingAtomGeneBox extends SwingGeneBox {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.arthro.genome.swing.SwingAtomGeneBox");

	
	
	public SwingAtomGeneBox() {
		this.add(valueLabel, BorderLayout.CENTER);
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
		StringBuffer text = new StringBuffer();
		text.append("<html>");
		text.append(newValue.toString());
		text.append("</html>");
		logger.info(text.toString());
		setText(text.toString());
	}
	@Override
	public void setGene(Gene gene) {
		super.setGene(gene);
		Atom atom = (Atom) gene;
		setValue(atom);
	}



}
