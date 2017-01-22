package net.richarddawkins.watchmaker.swing.engineer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EngineeringPanelMouseAdapter extends MouseAdapter  {

	protected EngineeringPanel engineeringPanel;
	public EngineeringPanelMouseAdapter(EngineeringPanel engineeringPanel) {
		this.engineeringPanel = engineeringPanel;
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
		// Raise message about hypodermic.
	}

}
