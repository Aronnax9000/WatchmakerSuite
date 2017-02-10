package net.richarddawkins.watchmaker.swing.album;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingAlbumMorphView extends SwingMorphView {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.album.SwingAlbumMorphView");

	public SwingAlbumMorphView(AppData appData, BoxedMorphCollection collection) {
		// TODO change to appropriate icon
		super(appData, "Hypodermic_PICT_03937_16x16", "Album", true, appData.isGeneBoxToSide());
		this.setBoxedMorphVector(collection);
		collection.getBoxes().setAccentuateMidBox(false);
		this.remove(centrePanel);

		JScrollPane scrollPane = new JScrollPane(centrePanel);
		this.add(scrollPane, BorderLayout.CENTER);
		((Component)centrePanel).setCursor(WatchmakerCursors.highlight);
	}

	public void boxClicked(Point myPt) {
		BoxManager boxes = boxedMorphVector.getBoxes();
		if(centrePanel.getCursor() == WatchmakerCursors.highlight) {
			int boxNo = boxes.getBoxNoContainingPoint(myPt, SwingGeom.toWatchmakerDim(centrePanel.getSize()));
			BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(boxNo);
			this.boxedMorphVector.setSelectedBoxedMorph(boxedMorph);
			pcs.firePropertyChange("genome", null, 
					boxedMorph.getMorph().getGenome());
			repaint();
		}
	}
	protected BoxedMorph selectedBoxedMorph = null;

	@Override
	public void processMouseMotion(Point myPt, Dim size) {
		
		if(!(centrePanel.getCursor() == WatchmakerCursors.highlight 
				&& boxedMorphVector.getSelectedBoxedMorph() != null)) {
			BoxManager boxes = boxedMorphVector.getBoxes();
			int boxNo = boxes.getBoxNoContainingPoint(myPt, size);
			if (boxNo != -1) {
				synchronized(boxedMorphVector) {
					BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(boxNo);
					
					if (boxedMorph != null) {
						if(boxedMorph != selectedBoxedMorph)
						pcs.firePropertyChange("genome", null, 
								boxedMorph.getMorph().getGenome());
						selectedBoxedMorph = boxedMorph;

					} 
				}
			}
		}
	}
	
    @Override
    public Morph getMorphOfTheHour() {
    	return boxedMorphVector.getSelectedBoxedMorph().getMorph();
    }

	/**
	 * 
	 */
	private static final long serialVersionUID = 8224824610112892419L;



	@Override
	public void seed(Morph morph) {
		// TODO Auto-generated method stub
		
	}


}
