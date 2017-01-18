package net.richarddawkins.watchmaker.morphs.biomorph.genome.swing;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;

import net.richarddawkins.watchmaker.swing.genebox.IntegerGeneBox;

public class IntegerGradientGeneBox extends IntegerGeneBox {
	
	private static final long serialVersionUID = 1631776161817013885L;
	protected GradientPanel gradientPanel;

	public IntegerGradientGeneBox(boolean engineeringMode) {
		super(engineeringMode);
		this.canGooseUpDn = true;
		gradientPanel = new GradientPanel(this);
		this.add(gradientPanel, BorderLayout.WEST);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("gradient"))
		{
			repaint();
		} else {
			super.propertyChange(evt);
		}
		
	}
}
