package net.richarddawkins.watchmaker.genome.gui;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public abstract class GeneBoxStrip extends JPanel {
	private static final long serialVersionUID = 1L;

	protected boolean engineeringMode;

	public abstract void setGenome(Genome genome);

	public abstract Genome getGenome();

	public abstract void setMorphConfig(MorphConfig morphConfig);

}
