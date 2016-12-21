package net.richarddawkins.watchmaker.gui.breed;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import net.richarddawkins.watchmaker.gui.breed.BreedingPanel.Phase;

public class BreedingPanelMouseAdapter extends MouseAdapter {

	private BreedingPanel panel;
	public BreedingPanelMouseAdapter(BreedingPanel panel) {
		this.panel = panel;
	}
	public void mousePressed(MouseEvent e) {
		
		Point myPt = e.getPoint();
		switch(panel.phase) {
		case breed_complete:
			  panel.special = panel.boxesDrawer.getBoxNoContainingPoint(myPt, panel.getSize());
			  System.out.println("Mouse pressed in box " + panel.special);
			  panel.phase = Phase.mouse_clicked;
			  panel.repaint();
			  break;
	    default:
		}
		
	}
}
