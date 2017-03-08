package net.richarddawkins.watchmaker.swing.genebox;

import java.awt.BorderLayout;

import net.richarddawkins.watchmaker.app.AppData;

abstract public class SwingTextGeneBox extends SwingGeneBox {

	private static final long serialVersionUID = 1L;
	public SwingTextGeneBox(AppData appData) {
	    super(appData);
		add(valueLabel, BorderLayout.CENTER);
	}

}
