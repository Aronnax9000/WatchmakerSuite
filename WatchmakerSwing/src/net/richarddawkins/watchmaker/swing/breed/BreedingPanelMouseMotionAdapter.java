package net.richarddawkins.watchmaker.swing.breed;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.Morph;

public class BreedingPanelMouseMotionAdapter implements MouseMotionListener {

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
			int boxNo = panel.getBoxesDrawer().getBoxNoContainingPoint(myPt, panel.getSize());
			  if(boxNo != -1) {
				  Morph morph = panel.getBoxedMorphVector().getBoxedMorph(boxNo).getMorph();
				  if(morph != null) {
					  GeneBoxStrip geneBoxStrip = (GeneBoxStrip)panel.
							  getWatchmakerPanel().getUpperStrip();
							  
					  geneBoxStrip.setGenome(morph.getGenome());
				  }
			  }
			  break;
	    default:
		}
		
	}
}
