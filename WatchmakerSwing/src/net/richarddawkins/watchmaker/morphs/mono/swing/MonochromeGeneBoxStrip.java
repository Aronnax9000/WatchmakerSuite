package net.richarddawkins.watchmaker.morphs.mono.swing;

import net.richarddawkins.watchmaker.genome.gui.IntegerGeneBox;
import net.richarddawkins.watchmaker.genome.gui.SimpleGeneBoxStrip;
import net.richarddawkins.watchmaker.gui.SwingAppData;
import net.richarddawkins.watchmaker.morphs.biomorph.genome.swing.CompletenessGeneBox;
import net.richarddawkins.watchmaker.morphs.biomorph.genome.swing.IntegerGradientGeneBox;
import net.richarddawkins.watchmaker.morphs.biomorph.genome.swing.SpokesGeneBox;

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
