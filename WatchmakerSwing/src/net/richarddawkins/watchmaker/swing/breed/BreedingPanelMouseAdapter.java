package net.richarddawkins.watchmaker.swing.breed;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.swing.breed.BreedingPanel.Phase;

public class BreedingPanelMouseAdapter extends MouseAdapter {

	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.gui.breed.BreedingPanelMouseAdapter");
	
	
	private BreedingPanel panel;
	public BreedingPanelMouseAdapter(BreedingPanel panel) {
		this.panel = panel;
	}
	
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		Point myPt = e.getPoint();
		switch(panel.phase) {
		case breed_complete:
			  panel.special = panel.getBoxesDrawer().getBoxNoContainingPoint(myPt, panel.getSize());
			  logger.log(Level.INFO, "Mouse pressed in box " + panel.special);
			  panel.phase = Phase.mouse_clicked;
			  panel.repaint();
			  break;
	    default:
		}
		
	}
	

}
