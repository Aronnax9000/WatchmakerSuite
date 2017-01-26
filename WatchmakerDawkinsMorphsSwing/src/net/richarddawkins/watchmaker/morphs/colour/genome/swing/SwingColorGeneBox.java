package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import net.richarddawkins.watchmaker.swing.SwingColor;
import net.richarddawkins.watchmaker.swing.genebox.SwingIntegerGeneBox;

public class SwingColorGeneBox extends SwingIntegerGeneBox {


	private static final long serialVersionUID = -5076715671424518452L;

	public SwingColorGeneBox() {
	}
	

	public void setValue(int value) {
		this.setBackground(SwingColor.rgbColorPalette[value]);
		this.valueLabel.setBackground(SwingColor.rgbColorPalette[value]);
		super.setValue(value);
	}

}
