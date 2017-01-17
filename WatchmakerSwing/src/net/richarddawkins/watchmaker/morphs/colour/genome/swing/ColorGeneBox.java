package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.genome.gui.IntegerGeneBox;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourPic;

public class ColorGeneBox extends IntegerGeneBox {


	private static final long serialVersionUID = -5076715671424518452L;

	public ColorGeneBox(GeneBoxStrip geneBoxStrip, boolean engineeringMode) {
		super(geneBoxStrip, engineeringMode);
	}
	
	@Override
	public void setValue(int value) {
		this.setBackground(ColourPic.rgbColorPalette[value]);
		this.valueLabel.setBackground(ColourPic.rgbColorPalette[value]);
		super.setValue(value);
	}

}
