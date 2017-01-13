package net.richarddawkins.watchmaker.morph.mono.gui;

import net.richarddawkins.watchmaker.genome.gui.IntegerGeneBox;
import net.richarddawkins.watchmaker.genome.gui.SimpleGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.biomorph.genome.gui.CompletenessGeneBox;
import net.richarddawkins.watchmaker.morph.biomorph.genome.gui.IntegerGradientGeneBox;
import net.richarddawkins.watchmaker.morph.biomorph.genome.gui.SpokesGeneBox;

public class MonochromeGeneBoxStrip extends SimpleGeneBoxStrip {

	private static final long serialVersionUID = 1L;
	
	public MonochromeGeneBoxStrip(MorphConfig config, boolean engineeringMode) {
		super(config, engineeringMode);
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
