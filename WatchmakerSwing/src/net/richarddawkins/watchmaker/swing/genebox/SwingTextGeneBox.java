package net.richarddawkins.watchmaker.swing.genebox;

import java.awt.BorderLayout;
import java.awt.Dimension;

abstract public class SwingTextGeneBox extends SwingGeneBox {

	private static final long serialVersionUID = 1L;
	public SwingTextGeneBox() {
		add(valueLabel, BorderLayout.CENTER);
	}

}
