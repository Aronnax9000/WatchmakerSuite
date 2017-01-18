package net.richarddawkins.watchmaker.morphs.mono.swing;

import net.richarddawkins.watchmaker.morphs.biomorph.genome.swing.CompletenessGeneBox;
import net.richarddawkins.watchmaker.morphs.biomorph.genome.swing.IntegerGradientGeneBox;
import net.richarddawkins.watchmaker.morphs.biomorph.genome.swing.SpokesGeneBox;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.genebox.IntegerGeneBox;
import net.richarddawkins.watchmaker.swing.geneboxstrip.SimpleGeneBoxStrip;

public class MonochromeGeneBoxStrip extends SimpleGeneBoxStrip {

	private static final long serialVersionUID = 1L;
	
	public MonochromeGeneBoxStrip(SwingAppData swingAppData, boolean engineeringMode) {
		super(swingAppData, engineeringMode);
		this.add(new IntegerGradientGeneBox(engineeringMode));
		this.add(new IntegerGradientGeneBox(engineeringMode));
		this.add(new IntegerGradientGeneBox(engineeringMode));
		this.add(new IntegerGradientGeneBox(engineeringMode));
		this.add(new IntegerGradientGeneBox(engineeringMode));
		this.add(new IntegerGradientGeneBox(engineeringMode));
		this.add(new IntegerGradientGeneBox(engineeringMode));
		this.add(new IntegerGradientGeneBox(engineeringMode));
		this.add(new IntegerGradientGeneBox(engineeringMode));
		this.add(new IntegerGeneBox(engineeringMode));
		this.add(new IntegerGradientGeneBox(engineeringMode));
		this.add(new CompletenessGeneBox(engineeringMode));
		this.add(new SpokesGeneBox(engineeringMode));
		this.add(new IntegerGeneBox(engineeringMode));
		this.add(new IntegerGeneBox(engineeringMode));
		this.add(new IntegerGeneBox(engineeringMode));
	}
}
