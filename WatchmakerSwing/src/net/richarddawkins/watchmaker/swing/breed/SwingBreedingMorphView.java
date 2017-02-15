package net.richarddawkins.watchmaker.swing.breed;

import java.util.Timer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.geom.BoxManager;
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

	protected boolean busyBreeding = false;
	
	protected BoxedMorph boxedMorphSpecial;

	Vector<Morph> litter;

	public int special = -1;
	Timer timer = new Timer();

	public SwingBreedingMorphView(AppData appData, Morph morph) {
		super(appData, "IconFlipBirdToBreedingGrid_ICON_00261_32x32", "Breeding", false, appData.isGeneBoxToSide());
		centrePanel.setCursor(WatchmakerCursors.breed);
		boxedMorphVector.setBoxes(new GridBoxManager(appData.getDefaultBreedingCols(), appData.getDefaultBreedingRows()));
		if(morph != null) {
			MorphConfig config = appData.getMorphConfig();
			Morph copy = config.newMorph();
			Genome genome = config.newGenome();
			morph.getGenome().copy(genome);
			copy.setGenome(genome);
			seed(copy);
		} else {
			seed(null);
		}
		
	}
	@Override
	public void boxClicked(Point myPt) {
		logger.info("SwingBreedingMorphView.boxClicked(" + myPt + ")");
		BoxManager boxes = boxedMorphVector.getBoxes();
		if(centrePanel.getCursor() == WatchmakerCursors.breed) {
			int boxNo = boxes.getBoxNoContainingPoint(myPt, SwingGeom.toWatchmakerDim(centrePanel.getSize()));
			if (boxNo != -1) {
				special = boxNo;
				breedFromSpecial();
			}
		} else if(centrePanel.getCursor() == WatchmakerCursors.random) {
			centrePanel.setCursor(WatchmakerCursors.watchCursor);
			seed(appData.getMorphConfig().newMorph(0));
			centrePanel.setCursor(null);
			updateCursor();
		}  else if(centrePanel.getCursor() == WatchmakerCursors.highlight) {
			int boxNo = boxes.getBoxNoContainingPoint(myPt, SwingGeom.toWatchmakerDim(centrePanel.getSize()));
			BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(boxNo);
			this.boxedMorphVector.setSelectedBoxedMorph(boxedMorph);
			pcs.firePropertyChange("genome", null, 
					boxedMorph.getMorph().getGenome());
			repaint();
			
		}
	}

	public void breedFromSpecial() {
		logger.log(Level.INFO, "Breeding from box " + special);
		centrePanel.setCursor(WatchmakerCursors.watchCursor);
		BoxAnimator animator = new BoxAnimator(this, special);
		timer.schedule(animator, 0, appData.getTickDelay());
	}

	@Override
	public Morph getMorphOfTheHour() {
		BoxedMorph boxedMorph = boxedMorphVector.getSelectedBoxedMorph();
		if(boxedMorph != null) {
			return boxedMorph.getMorph();
		} else {
			BoxManager boxes = boxedMorphVector.getBoxes();

			return boxedMorphVector.getBoxedMorph(boxes.getMidBox()).getMorph();
		}
	}

	protected int selectedBoxNo = -1;

	
	@Override
	public void processMouseMotion(Point myPt, Dim size) {
		logger.info("processMouseMotion(" + myPt + ", " + size);
		if(!(centrePanel.getCursor() == WatchmakerCursors.highlight 
				&& boxedMorphVector.getSelectedBoxedMorph() != null)) {
			BoxManager boxes = boxedMorphVector.getBoxes();
			int boxNo = boxes.getBoxNoContainingPoint(myPt, size);
			if (boxNo != -1) {
				synchronized(boxedMorphVector) {
					BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(boxNo);
					
					if (boxedMorph != null) {
						if(boxNo != selectedBoxNo)
						pcs.firePropertyChange("genome", null, 
								boxedMorph.getMorph().getGenome());
						selectedBoxNo = boxNo;
	
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
			} else {
				logger.warning("boxNo is -1");
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
		BoxManager boxes = boxedMorphVector.getBoxes();

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
