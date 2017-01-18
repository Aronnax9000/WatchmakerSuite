package net.richarddawkins.watchmaker.swing.genome;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.SwingAppData;

public abstract class GeneBoxStrip extends JPanel {
	private static final long serialVersionUID = 1L;

	protected boolean engineeringMode;
	protected SwingAppData swingAppData;
	
	public GeneBoxStrip(SwingAppData swingAppData, boolean engineeringMode) {
		this.swingAppData = swingAppData;
		this.engineeringMode = engineeringMode;
	}

	public abstract void setGenome(Genome genome);

	public abstract Genome getGenome();

	public abstract void setMorphConfig(MorphConfig morphConfig);

}
