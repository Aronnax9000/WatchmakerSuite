package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import net.richarddawkins.watchmaker.genome.gui.IntegerGeneBox;
import net.richarddawkins.watchmaker.gui.SwingAppData;
import net.richarddawkins.watchmaker.morphs.mono.swing.MonochromeGeneBoxStrip;

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
