package net.richarddawkins.watchmaker.morphs.arthro.genome.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.morphs.arthro.genome.Atom;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingGeneBox;

public class SwingAtomGeneBox extends SwingGeneBox {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.arthro.genome.swing.SwingAtomGeneBox");

	public JPanel firstBelowMePanel = new JPanel() {
		private static final long serialVersionUID = 1L;
	};

	public JPanel likeMePanel = new JPanel() {
		private static final long serialVersionUID = 1L;
	};
	
	
	public SwingAtomGeneBox() {
		likeMePanel.setLayout(new GridLayout(0,1));
		likeMePanel.add(this.valueLabel);
		likeMePanel.add(firstBelowMePanel);
		add(likeMePanel, BorderLayout.CENTER);
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
		String text = newValue.toString();
		logger.info(text);
		setText(text);
	}
	@Override
	public void setGene(Gene gene) {
		super.setGene(gene);
		Atom atom = (Atom) gene;
		setValue(atom);
		if(atom.firstBelowMe != null) {
			GeneBox firstBelowMeGeneBox = new SwingAtomGeneBox();
			firstBelowMePanel.add((Component)firstBelowMeGeneBox);
			firstBelowMeGeneBox.setGene(atom.firstBelowMe);
		}
		if(atom.nextLikeMe != null) {
			GeneBox nextLikeMeGeneBox = new SwingAtomGeneBox();
			likeMePanel.add((Component)nextLikeMeGeneBox);
			nextLikeMeGeneBox.setGene(atom.nextLikeMe);
		}
	}



}
