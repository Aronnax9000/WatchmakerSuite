package net.richarddawkins.watchmaker.swing.breed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Timer;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.components.SwingBorderLayoutMorphView;
import net.richarddawkins.watchmaker.swing.morphview.SwingBoxedMorphViewPanel;

public class SwingBreedingBoxedMorphViewPanel extends SwingBoxedMorphViewPanel implements ActionListener {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.breed.BreedingPanel");

	enum Phase {
		animate_mother, breed_complete, draw_out_offspring, reactivate_grid
	}

	private static final long serialVersionUID = 8668997028542499649L;
	protected BoxedMorph boxedMorphSpecial;

	protected MouseAdapter mouseAdapter;
	Vector<Morph> litter;
	private BoxedMorph newestOffspring = null;
	public Phase phase = Phase.breed_complete;
	public int special = -1;
	Timer timer = new Timer(1000 / 60, this);

	private int vacantBoxNumber = -1;

	protected SwingBorderLayoutMorphView watchmakerPanel;

	public SwingBreedingBoxedMorphViewPanel(SwingBorderLayoutMorphView parentMorphView) {
		super(parentMorphView.getAppData());
		this.watchmakerPanel = parentMorphView;
		AppData appData = parentMorphView.getAppData();
		boxes = new GridBoxManager(appData.getDefaultBreedingCols(), appData.getDefaultBreedingRows());
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

				Point myPt = e.getPoint();
				switch (phase) {
				case breed_complete:

					int boxNo = getBoxes().getBoxNoContainingPoint(SwingGeom.toWatchmakerPoint(myPt),
							SwingGeom.toWatchmakerDim(getSize()));
					if (boxNo != -1) {

						BoxedMorph boxedMorph = getBoxedMorphVector().getBoxedMorph(boxNo);
						if (boxedMorph != null) {
							GeneBoxStrip geneBoxStrip = (GeneBoxStrip) getWatchmakerPanel().getUpperStrip();
							geneBoxStrip.setGenome(boxedMorph.getMorph().getGenome());
						}
					}
					break;
				default:
				}
			}
		});
		setPreferredSize(new Dimension(640, 480));
		setBorder(BorderFactory.createLineBorder(Color.black));

		mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				boxClicked(e.getPoint());
			}
		};
		this.addMouseListener(mouseAdapter);
	}

	public void boxClicked(Point myPt) {
		switch (phase) {
		case breed_complete:
			int boxNo = getBoxes().getBoxNoContainingPoint(SwingGeom.toWatchmakerPoint(myPt),
					SwingGeom.toWatchmakerDim(getSize()));
				if(boxNo != -1) {
					special = boxNo;
					logger.log(Level.INFO, "Mouse pressed in box " + special);
					boxedMorphSpecial = boxedMorphVector.getBoxedMorph(special);
					if(boxedMorphSpecial != null) {
						boxedMorphVector.removeAllElements();
						int midBox = boxes.getMidBox();
						if (special != midBox) {
							boxedMorphSpecial.setDestinationBoxNo(midBox);
							boxedMorphSpecial.setProgress(0.0d);
							boxedMorphSpecial.setScaleWithProgress(false);
							showBoxes = false;
							phase = Phase.animate_mother;
						} else {
							phase = Phase.reactivate_grid;
						}
						boxedMorphVector.add(boxedMorphSpecial);
						timer.start();
					}
					break;
				}
			break;
		default:
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.repaint();
	}

	public SwingBorderLayoutMorphView getWatchmakerPanel() {
		return watchmakerPanel;
	}

	public void seed(Morph morph) {

		Morph parent;
		if (morph == null) {
			MorphConfig config = watchmakerPanel.getAppData().getMorphConfig();
			parent = config.newMorph(0);

		} else {
			parent = morph;
		}
		int midBox = boxes.getMidBox();
		BoxedMorph boxedMorph = new BoxedMorph(boxes, parent, midBox);
		boxedMorphVector.add(boxedMorph);
		GeneBoxStrip geneBoxStrip = (GeneBoxStrip) watchmakerPanel.getUpperStrip();
		geneBoxStrip.setGenome(parent.getGenome());
		// Trigger first breeding
		special = midBox;
		phase = Phase.breed_complete;

	}

	public void setWatchmakerPanel(SwingBorderLayoutMorphView watchmakerPanel) {
		this.watchmakerPanel = watchmakerPanel;
	}

	protected void updateModel(Dim size) {

		int midBox = boxes.getMidBox();

		switch (phase) {
		case breed_complete:
			logger.info("Breed Complete");

			special = -1;
			boxedMorphSpecial = null;
			timer.stop();

			GeneBoxStrip geneBoxStrip = (GeneBoxStrip) watchmakerPanel.getUpperStrip();
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
			timer.stop();
			boxedMorphSpecial.setBoxNo(midBox);
			boxedMorphSpecial.setDestinationBoxNo(-1);
			boxedMorphSpecial.setProgress(0.0d);
			Morph parentMorph = boxedMorphSpecial.getMorph();
			int litterSize = boxes.getBoxCount() - 1;
			MorphConfig config = appData.getMorphConfig();
			litter = config.getLitter(parentMorph, litterSize);
			logger.info("Reactivate Grid asked for litter of size " + litterSize + ", got: " + litter.size());
			showBoxes = true;
			vacantBoxNumber = 0;
			phase = Phase.draw_out_offspring;
			timer.start();
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
						timer.restart();
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

			timer.restart();
			break;

		default:
		}

	}


}
