package net.richarddawkins.watchmaker.morph.colour;

import net.richarddawkins.watchmaker.genome.gui.IntegerGeneBox;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.mono.gui.MonochromeGeneBoxStrip;

public class ColourGeneBoxStrip extends MonochromeGeneBoxStrip {

	private static final long serialVersionUID = 1L;

	public ColourGeneBoxStrip(MorphConfig config, boolean engineeringMode) {
	    super(config, engineeringMode);
		this.add(new ColorGeneBox(this, engineeringMode));
		this.add(new ColorGeneBox(this, engineeringMode));
		this.add(new ColorGeneBox(this, engineeringMode));
		this.add(new ColorGeneBox(this, engineeringMode));
		this.add(new ColorGeneBox(this, engineeringMode));
		this.add(new ColorGeneBox(this, engineeringMode));
		this.add(new ColorGeneBox(this, engineeringMode));
		this.add(new ColorGeneBox(this, engineeringMode));
		this.add(new ColorGeneBox(this, engineeringMode));
		this.add(new LimbShapeGeneBox(this, engineeringMode));
		this.add(new LimbFillGeneBox(this, engineeringMode));
		this.add(new IntegerGeneBox(this, engineeringMode));
	  }
}
