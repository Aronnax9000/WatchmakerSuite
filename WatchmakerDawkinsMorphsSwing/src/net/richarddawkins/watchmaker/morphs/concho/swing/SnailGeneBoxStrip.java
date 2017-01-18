package net.richarddawkins.watchmaker.morphs.concho.swing;

import java.awt.GridBagConstraints;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphs.concho.SnailGenome;
import net.richarddawkins.watchmaker.swing.genebox.DoubleGeneBox;
import net.richarddawkins.watchmaker.swing.geneboxstrip.SimpleGeneBoxStrip;

public class SnailGeneBoxStrip extends SimpleGeneBoxStrip {

	private static final long serialVersionUID = 1L;

	private void updateGeneBox(int geneBoxIndex) {
	}

	public void setGenome(Genome genome) {
		this.genome = (SnailGenome) genome;
		for (int j = 0; j < this.getComponentCount(); j++)
			updateGeneBox(j);
	}

	public SnailGeneBoxStrip(SnailSwingAppData snailSwingAppData, boolean engineeringMode) {
		super(snailSwingAppData, engineeringMode);
		int numberOfGeneBoxes = snailSwingAppData.getMorphConfig().getGeneBoxCount();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		constraints.gridy = 0;
		for (int i = 0; i < numberOfGeneBoxes; i++) {
			DoubleGeneBox geneBox = new DoubleGeneBox(engineeringMode);
			constraints.gridx = i;
			this.add(geneBox, constraints);
		}

	}

	@Override
	public Genome getGenome() {
		return genome;
	}

}