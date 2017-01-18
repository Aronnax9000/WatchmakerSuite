package net.richarddawkins.watchmaker.morphs.mono.swing;

import net.richarddawkins.watchmaker.morphs.biomorph.genome.swing.CompletenessGeneBox;
import net.richarddawkins.watchmaker.morphs.biomorph.genome.swing.IntegerGradientGeneBox;
import net.richarddawkins.watchmaker.morphs.biomorph.genome.swing.SpokesGeneBox;
import net.richarddawkins.watchmaker.swing.SwingAppData;
import net.richarddawkins.watchmaker.swing.genome.IntegerGeneBox;
import net.richarddawkins.watchmaker.swing.genome.SimpleGeneBoxStrip;

public class MonochromeGeneBoxStrip extends SimpleGeneBoxStrip {

	private static final long serialVersionUID = 1L;
	
	public MonochromeGeneBoxStrip(SwingAppData swingAppData, boolean engineeringMode) {
		super(swingAppData, engineeringMode);
		this.add(new IntegerGradientGeneBox(this, engineeringMode));
		this.add(new IntegerGradientGeneBox(this, engineeringMode));
		this.add(new IntegerGradientGeneBox(this, engineeringMode));
		this.add(new IntegerGradientGeneBox(this, engineeringMode));
		this.add(new IntegerGradientGeneBox(this, engineeringMode));
		this.add(new IntegerGradientGeneBox(this, engineeringMode));
		this.add(new IntegerGradientGeneBox(this, engineeringMode));
		this.add(new IntegerGradientGeneBox(this, engineeringMode));
		this.add(new IntegerGradientGeneBox(this, engineeringMode));
		this.add(new IntegerGeneBox(this, engineeringMode));
		this.add(new IntegerGradientGeneBox(this, engineeringMode));
		this.add(new CompletenessGeneBox(this, engineeringMode));
		this.add(new SpokesGeneBox(this, engineeringMode));
		this.add(new IntegerGeneBox(this, engineeringMode));
		this.add(new IntegerGeneBox(this, engineeringMode));
		this.add(new IntegerGeneBox(this, engineeringMode));
	}
}
