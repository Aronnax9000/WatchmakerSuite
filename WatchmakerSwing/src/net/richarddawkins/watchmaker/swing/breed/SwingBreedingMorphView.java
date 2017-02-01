package net.richarddawkins.watchmaker.swing.breed;



import java.util.Timer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.genome.GenomeFactory;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingBreedingMorphView extends SwingMorphView {
	enum Phase {
		animate_mother, breed_complete, draw_out_offspring, reactivate_grid
	}

	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphView");
	private static final long serialVersionUID = -5445629768562940527L;
	
	protected BoxedMorph boxedMorphSpecial;

	
	Vector<Morph> litter;

	private BoxedMorph newestOffspring = null;
	public Phase phase = Phase.breed_complete;
	public int special = -1;
	Timer timer = new Timer();
	private int vacantBoxNumber = -1;
	public SwingBreedingMorphView(SwingAppData simpleSwingAppData, Morph morph) {
       	super(simpleSwingAppData, 
       			"IconFlipBirdToBreedingGrid_ICON_00261_32x32", 
       			"Breeding",
       			false);
       	centrePanel.setCursor(WatchmakerCursors.breed);
       	setBoxes(new GridBoxManager(appData.getDefaultBreedingCols(), appData.getDefaultBreedingRows()));
		

       	seed(morph);
    }
	public void boxClicked(Point myPt) {
		switch (phase) {
		case breed_complete:
			int boxNo = boxes.getBoxNoContainingPoint(myPt,
					SwingGeom.toWatchmakerDim(getSize()));
				if(boxNo != -1) {
					special = boxNo;
					breedFromSpecial();
				}
			break;
		default:
		}
	}

	public void breedFromSpecial() {
		logger.log(Level.INFO, "Breeding from box " + special);
		boxedMorphSpecial = boxedMorphVector.getBoxedMorph(special);
		if(boxedMorphSpecial != null) {
			boxedMorphVector.removeAllElements();
			int midBox = boxes.getMidBox();
			if (special != midBox) {
				boxedMorphSpecial.setDestinationBoxNo(midBox);
				boxedMorphSpecial.setProgress(0.0d);
				boxedMorphSpecial.setScaleWithProgress(false);
				setShowBoxes(false);
				phase = Phase.animate_mother;
			} else {
				phase = Phase.reactivate_grid;
			}
			boxedMorphVector.add(boxedMorphSpecial);
		} else {
			if(appData.isSaltOnEmptyBreedingBoxClick()) {
				this.seed(appData.getMorphConfig().newMorph(GenomeFactory.RANDOM));
			}
		}
	}
	@Override
    public Morph getMorphOfTheHour() {
    	return boxedMorphVector
    			.getBoxedMorph(boxes.getMidBox())
    			.getMorph();
    }
    @Override
	public void processMouseMotion(Point myPt) {
		
		switch (phase) {
		case breed_complete:
			int boxNo = boxes.getBoxNoContainingPoint(myPt,
					SwingGeom.toWatchmakerDim(getSize()));
			if (boxNo != -1) {
				BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(boxNo);
				if (boxedMorph != null) {
					GeneBoxStrip geneBoxStrip = (GeneBoxStrip) getUpperStrip();
					geneBoxStrip.setGenome(boxedMorph.getMorph().getGenome());
					setCursor(WatchmakerCursors.breed);
				} else {
					setCursor(WatchmakerCursors.random);
				}
			}
			break;
		default:
		}
	}
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
		if(appData.isBreedRightAway()) {
			breedFromSpecial();
		} else {
			phase = Phase.breed_complete; 
		}
		parent.setImage(null);
		repaint();

	}
    
    
	@Override
	public void updateModel(Dim size) {
		int midBox = boxes.getMidBox();

		switch (phase) {
		case breed_complete:
			logger.info("Breed Complete");

			special = -1;
			boxedMorphSpecial = null;

			GeneBoxStrip geneBoxStrip = (GeneBoxStrip) getUpperStrip();
			geneBoxStrip.setGenome(boxedMorphVector.getBoxedMorph(midBox).getMorph().getGenome());
			break;

		case animate_mother:
			logger.info("Animate Mother");
			boxedMorphSpecial.nudge();
			if (boxedMorphSpecial.getProgress() == 1.0d)
				phase = Phase.reactivate_grid;
			break;
		case reactivate_grid:
			logger.info("Reactivate Grid");
			boxedMorphSpecial.setBoxNo(midBox);
			boxedMorphSpecial.setDestinationBoxNo(-1);
			boxedMorphSpecial.setProgress(0.0d);
			Morph parentMorph = boxedMorphSpecial.getMorph();
			int litterSize = boxes.getBoxCount() - 1;
			MorphConfig config = appData.getMorphConfig();
			litter = config.getLitter(parentMorph, litterSize);
			logger.info("Reactivate Grid asked for litter of size " + litterSize + ", got: " + litter.size());
			setShowBoxes(true);
			vacantBoxNumber = 0;
			phase = Phase.draw_out_offspring;
			break;
		case draw_out_offspring:
			if (newestOffspring == null) {
				newestOffspring = new BoxedMorph(boxes, litter.get(0), boxes.getMidBox());
				newestOffspring.setDestinationBoxNo(vacantBoxNumber);
				logger.info("DrawOutOffSpring:" + newestOffspring.getBoxNo() + " to "
						+ newestOffspring.getDestinationBoxNo());
				vacantBoxNumber++;
				newestOffspring.setScaleWithProgress(true);
				boxedMorphVector.add(newestOffspring);
			} else {
				newestOffspring.getMorph().setImage(null);
				newestOffspring.nudge();
				if (newestOffspring.getProgress() == 1.0d) {
					// newestOffspring has made it to its new home box.
					newestOffspring.setBoxNo(newestOffspring.getDestinationBoxNo());
					newestOffspring.setDestinationBoxNo(-1);
					newestOffspring.setProgress(0.0d);
					newestOffspring.setScaleWithProgress(false);
					Morph youngerSib = newestOffspring.getMorph().getPedigree().youngerSib;

					if (youngerSib == null) {
						// This is the last morph.
						phase = Phase.breed_complete;
						newestOffspring = null;

					} else {
						// Create a new BoxedMorph from the next offspring
						newestOffspring = new BoxedMorph(boxes, youngerSib, midBox);
						if (vacantBoxNumber == midBox) {
							vacantBoxNumber++;
						}
						newestOffspring.setDestinationBoxNo(vacantBoxNumber);
						vacantBoxNumber++;
						newestOffspring.setScaleWithProgress(true);
						boxedMorphVector.add(newestOffspring);
					}
				}
				break;
			}

			break;

		default:
		}

	}
}
