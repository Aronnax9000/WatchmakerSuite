package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import net.richarddawkins.watchmaker.morphs.mono.swing.MonochromeGeneBoxStrip;
import net.richarddawkins.watchmaker.swing.SwingAppData;
import net.richarddawkins.watchmaker.swing.genome.IntegerGeneBox;

public class ColourGeneBoxStrip extends MonochromeGeneBoxStrip {

	private static final long serialVersionUID = 1L;

	public ColourGeneBoxStrip(SwingAppData swingAppData, boolean engineeringMode) {
	    super(swingAppData, engineeringMode);
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
