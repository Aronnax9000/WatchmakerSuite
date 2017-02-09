package net.richarddawkins.watchmaker.swing.breed;

import java.util.Timer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingBreedingMorphView extends SwingMorphView {

	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphView");
	private static final long serialVersionUID = -5445629768562940527L;

	protected BoxedMorph boxedMorphSpecial;

	Vector<Morph> litter;

	public int special = -1;
	Timer timer = new Timer();

	public SwingBreedingMorphView(AppData appData, Morph morph) {
		super(appData, "IconFlipBirdToBreedingGrid_ICON_00261_32x32", "Breeding", false, appData.isGeneBoxToSide());
		centrePanel.setCursor(WatchmakerCursors.breed);
		setBoxes(new GridBoxManager(appData.getDefaultBreedingCols(), appData.getDefaultBreedingRows()));

		seed(morph);
	}
	
	public void boxClicked(Point myPt) {
		if(centrePanel.getCursor() == WatchmakerCursors.breed) {
			int boxNo = boxes.getBoxNoContainingPoint(myPt, SwingGeom.toWatchmakerDim(centrePanel.getSize()));
			if (boxNo != -1) {
				special = boxNo;
				breedFromSpecial();
			}
		} else if(centrePanel.getCursor() == WatchmakerCursors.random) {
			centrePanel.setCursor(WatchmakerCursors.watchCursor);
			seed(appData.getMorphConfig().newMorph(0));
			centrePanel.setCursor(WatchmakerCursors.random);
		}  else if(centrePanel.getCursor() == WatchmakerCursors.highlight) {
			int boxNo = boxes.getBoxNoContainingPoint(myPt, SwingGeom.toWatchmakerDim(centrePanel.getSize()));
			this.boxedMorphVector.setSelectedBoxedMorph(this.boxedMorphVector.getBoxedMorph(boxNo));
			repaint();
			
		}
	}

	public void breedFromSpecial() {
		logger.log(Level.INFO, "Breeding from box " + special);
		centrePanel.setCursor(WatchmakerCursors.watchCursor);
		BoxAnimator animator = new BoxAnimator(this, special);
		timer.schedule(animator, 0, 17);
	}

	@Override
	public Morph getMorphOfTheHour() {
		return boxedMorphVector.getBoxedMorph(boxes.getMidBox()).getMorph();
	}

	protected BoxedMorph selectedBoxedMorph = null;
	
	@Override
	public void processMouseMotion(Point myPt, Dim size) {
		int boxNo = boxes.getBoxNoContainingPoint(myPt, size);
		if (boxNo != -1) {
			synchronized(boxedMorphVector) {
				BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(boxNo);
				
				if (boxedMorph != null) {
					if(boxedMorph != selectedBoxedMorph)
					pcs.firePropertyChange("genome", null, 
							boxedMorph.getMorph().getGenome());
					selectedBoxedMorph = boxedMorph;

					if(centrePanel.getCursor() != WatchmakerCursors.watchCursor 
							&& centrePanel.getCursor() != WatchmakerCursors.highlight) {
						centrePanel.setCursor(WatchmakerCursors.breed);
					}
				} else {
					if(centrePanel.getCursor() != WatchmakerCursors.watchCursor 
							&& centrePanel.getCursor() != WatchmakerCursors.highlight) {
						centrePanel.setCursor(WatchmakerCursors.random);
					}
				}
			}
		}
	}

	@Override
	public void seed(Morph morph) {

		Morph parent;
		if (morph == null) {
			MorphConfig config = appData.getMorphConfig();
			parent = config.newMorph(config.getStartingMorphBasicType());
		} else {
			parent = morph;
		}
		int midBox = boxes.getMidBox();
		BoxedMorph boxedMorph = new BoxedMorph(boxes, parent, midBox);
		boxedMorphVector.removeAllElements();
		boxedMorphVector.add(boxedMorph);
		pcs.firePropertyChange("genome", null, 
				boxedMorph.getMorph().getGenome());
		// Trigger first breeding
		special = midBox;
		if (appData.isBreedRightAway()) {
			breedFromSpecial();
		}
		parent.setImage(null);
		repaint();

	}
}
