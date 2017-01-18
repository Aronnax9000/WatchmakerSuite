package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import net.richarddawkins.watchmaker.morphs.mono.swing.MonochromeGeneBoxStrip;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.genebox.IntegerGeneBox;

public class ColourGeneBoxStrip extends MonochromeGeneBoxStrip {

	private static final long serialVersionUID = 1L;

	public ColourGeneBoxStrip(SwingAppData swingAppData, boolean engineeringMode) {
	    super(swingAppData, engineeringMode);
		this.add(new ColorGeneBox(engineeringMode));
		this.add(new ColorGeneBox(engineeringMode));
		this.add(new ColorGeneBox(engineeringMode));
		this.add(new ColorGeneBox(engineeringMode));
		this.add(new ColorGeneBox(engineeringMode));
		this.add(new ColorGeneBox(engineeringMode));
		this.add(new ColorGeneBox(engineeringMode));
		this.add(new ColorGeneBox(engineeringMode));
		this.add(new ColorGeneBox(engineeringMode));
		this.add(new LimbShapeGeneBox(engineeringMode));
		this.add(new LimbFillGeneBox(engineeringMode));
		this.add(new IntegerGeneBox(engineeringMode));
	  }
}
