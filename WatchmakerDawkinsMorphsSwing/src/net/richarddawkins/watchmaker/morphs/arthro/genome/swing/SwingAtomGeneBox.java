package net.richarddawkins.watchmaker.morphs.arthro.genome.swing;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morphs.arthro.genome.Atom;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingGeneBox;

public class SwingAtomGeneBox extends SwingGeneBox {

	public JPanel firstBelowMePanel = new JPanel() {
		private static final long serialVersionUID = 1L;
	};

	public SwingAtomGeneBox() {
		this.add(this.valueLabel, BorderLayout.PAGE_START);
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
		setText(newValue.kind.toString());
		
		
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}

}
