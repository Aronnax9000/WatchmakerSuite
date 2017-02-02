package net.richarddawkins.watchmaker.swing.breed;

import java.util.Timer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingBreedingMorphView extends SwingMorphView {

	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphView");
	private static final long serialVersionUID = -5445629768562940527L;

	protected BoxedMorph boxedMorphSpecial;

	Vector<Morph> litter;

	public int special = -1;
	Timer timer = new Timer();

	public SwingBreedingMorphView(SwingAppData simpleSwingAppData, Morph morph) {
		super(simpleSwingAppData, "IconFlipBirdToBreedingGrid_ICON_00261_32x32", "Breeding", false);
		centrePanel.setCursor(WatchmakerCursors.breed);
		setBoxes(new GridBoxManager(appData.getDefaultBreedingCols(), appData.getDefaultBreedingRows()));

		seed(morph);
	}
	
	public void boxClicked(Point myPt) {
		if(centrePanel.getCursor() == WatchmakerCursors.breed) {
			int boxNo = boxes.getBoxNoContainingPoint(myPt, SwingGeom.toWatchmakerDim(getSize()));
			if (boxNo != -1) {
				special = boxNo;
				breedFromSpecial();
			}
		} else if(centrePanel.getCursor() == WatchmakerCursors.random) {
			seed(appData.getMorphConfig().newMorph(0));
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

	@Override
	public void processMouseMotion(Point myPt) {
		int boxNo = boxes.getBoxNoContainingPoint(myPt, SwingGeom.toWatchmakerDim(getSize()));
		if (boxNo != -1) {
			synchronized(boxedMorphVector) {
				BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(boxNo);
				if (boxedMorph != null) {
					GeneBoxStrip geneBoxStrip = (GeneBoxStrip) getUpperStrip();
					geneBoxStrip.setGenome(boxedMorph.getMorph().getGenome());
					if(centrePanel.getCursor() != WatchmakerCursors.watchCursor) {
						centrePanel.setCursor(WatchmakerCursors.breed);
					}
				} else {
					if(centrePanel.getCursor() != WatchmakerCursors.watchCursor) {
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

		GeneBoxStrip geneBoxStrip = (GeneBoxStrip) getUpperStrip();
		geneBoxStrip.setGenome(parent.getGenome());
		// Trigger first breeding
		special = midBox;
		if (appData.isBreedRightAway()) {
			breedFromSpecial();
		}
		parent.setImage(null);
		repaint();

	}
}
