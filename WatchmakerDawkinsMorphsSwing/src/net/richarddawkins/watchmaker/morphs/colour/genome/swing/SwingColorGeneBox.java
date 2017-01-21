package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import net.richarddawkins.watchmaker.morphs.colour.geom.ColourPic;
import net.richarddawkins.watchmaker.swing.genebox.SwingIntegerGeneBox;

public class SwingColorGeneBox extends SwingIntegerGeneBox {


	private static final long serialVersionUID = -5076715671424518452L;

	public SwingColorGeneBox() {
	}
	

	public void setValue(int value) {
		this.setBackground(ColourPic.rgbColorPalette[value]);
		this.valueLabel.setBackground(ColourPic.rgbColorPalette[value]);
		super.setValue(value);
	}

}
