package net.richarddawkins.watchmaker.swing.genebox;

import java.awt.BorderLayout;

abstract public class SwingTextGeneBox extends SwingGeneBox {

	private static final long serialVersionUID = 1L;
	public SwingTextGeneBox() {
		add(valueLabel, BorderLayout.CENTER);
	}

}
