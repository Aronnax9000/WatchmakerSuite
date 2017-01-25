package net.richarddawkins.watchmaker.swing.breed;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.swing.SwingGeom;

public class BreedingPanelMouseMotionAdapter implements MouseMotionListener {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.breed.BreedingPanelMouseMotionAdapter");

	private BreedingPanel panel;
	public BreedingPanelMouseMotionAdapter(BreedingPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {


	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		Point myPt = e.getPoint();
		switch(panel.phase) {
		case breed_complete:
			
			int boxNo = panel.getBoxes().getBoxNoContainingPoint(
					SwingGeom.toWatchmakerPoint(myPt), 
					SwingGeom.toWatchmakerDim(panel.getSize()));
			  if(boxNo != -1) {
				  
				  BoxedMorph boxedMorph = panel.getBoxedMorphVector().getBoxedMorph(boxNo);
				  
				  if(boxedMorph != null) {
					  
					  GeneBoxStrip geneBoxStrip = (GeneBoxStrip)panel.
							  getWatchmakerPanel().getUpperStrip();
							  
					  geneBoxStrip.setGenome(boxedMorph.getMorph().getGenome());
				  }
			  }
			  break;
	    default:
		}
		
	}
}
