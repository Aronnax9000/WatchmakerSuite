package net.richarddawkins.watchmaker.swing.breed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Timer;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Boxes;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.components.SwingBorderLayoutMorphView;
import net.richarddawkins.watchmaker.swing.morphview.BoxyMorphViewPanel;

public class BreedingPanel extends BoxyMorphViewPanel implements ActionListener {
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.breed.BreedingPanel");

    enum Phase {
        animate_mother, breed_complete, draw_out_offspring, mouse_clicked, reactivate_grid
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

    public BreedingPanel(SwingBorderLayoutMorphView watchmakerPanel) {
        super(watchmakerPanel.getSwingAppData());
        this.watchmakerPanel = watchmakerPanel;
        SwingAppData swingAppData = watchmakerPanel.getSwingAppData();
        boxes = new Boxes(swingAppData.getDefaultBreedingCols(), swingAppData.getDefaultBreedingRows());
        mouseAdapter = new BreedingPanelMouseAdapter(this);
        this.addMouseMotionListener(new BreedingPanelMouseMotionAdapter(this));
        this.addMouseListener(mouseAdapter);
        setPreferredSize(new Dimension(640, 480));
        setBorder(BorderFactory.createLineBorder(Color.black));

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
        	MorphConfig config = watchmakerPanel.getSwingAppData().getMorphConfig();
        	parent = config.newMorph(1);
        
        } else {
            parent = morph;
        }
        
        BoxedMorph boxedMorph = new BoxedMorph(boxes, parent, boxes.midBox);
        boxedMorphVector.add(boxedMorph);
        GeneBoxStrip geneBoxStrip = (GeneBoxStrip) watchmakerPanel.getUpperStrip();
        geneBoxStrip.setGenome(parent.getGenome());
        // Trigger first breeding
        special = boxes.midBox;
        phase = Phase.breed_complete;

    }

    public void setWatchmakerPanel(SwingBorderLayoutMorphView watchmakerPanel) {
        this.watchmakerPanel = watchmakerPanel;
    }

    
    protected void updateModel(Dim size) {
        

        switch (phase) {
        case breed_complete:
        	logger.info("Breed Complete");

            special = -1;
            boxedMorphSpecial = null;
            timer.stop();
//            BoxedMorph[] boxedMorphs = boxedMorphVector.getBoxedMorphs().toArray(new BoxedMorph[0]);
//            for (int boxNo = 0; boxNo < boxedMorphs.length; boxNo++) {
//            		boxedMorphs[boxNo].setBoxNo(boxNo);
//            }

            GeneBoxStrip geneBoxStrip = (GeneBoxStrip) watchmakerPanel.getUpperStrip();
            geneBoxStrip.setGenome(boxedMorphVector.getBoxedMorph(boxes.midBox).getMorph().getGenome());
            break;
        case mouse_clicked:
        	logger.info("Mouse Clicked");

            boxedMorphSpecial = boxedMorphVector.getBoxedMorph(special);
            boxedMorphVector.removeAllElements();
            if(special != boxes.midBox) {
	            boxedMorphSpecial.setDestinationBoxNo(boxes.midBox);
	            boxedMorphSpecial.setProgress(0.0d);
	            boxedMorphSpecial.setScaleWithProgress(false);
	            showBoxes = false;
	            phase = Phase.animate_mother;
            } else {
            	phase = Phase.reactivate_grid;
            }
            boxedMorphVector.add(boxedMorphSpecial);
            timer.start();
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
            boxedMorphSpecial.setBoxNo(boxes.midBox);
            boxedMorphSpecial.setDestinationBoxNo(-1);
            boxedMorphSpecial.setProgress(0.0d);
            Morph parentMorph = boxedMorphSpecial.getMorph();
            int litterSize = boxes.boxCount - 1;
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
            	newestOffspring = new BoxedMorph(boxes, litter.get(0), boxes.midBox);
            	newestOffspring.setDestinationBoxNo(vacantBoxNumber);
            	logger.info("DrawOutOffSpring:" + newestOffspring.getBoxNo() + " to " + newestOffspring.getDestinationBoxNo());
            	vacantBoxNumber++;
                newestOffspring.setScaleWithProgress(true);
            	boxedMorphVector.add(newestOffspring);
            } else {
                newestOffspring.nudge();
                if (newestOffspring.getProgress() == 1.0d) {
                	// newestOffspring has made it to its new home box.
                	newestOffspring.setBoxNo(newestOffspring.getDestinationBoxNo());
                    newestOffspring.setDestinationBoxNo(-1);
                    newestOffspring.setProgress(0.0d);
                    newestOffspring.setScaleWithProgress(false);
                    Morph youngerSib = newestOffspring.getMorph().getPedigree().youngerSib;
                    
                    if(youngerSib == null) {
                    	// This is the last morph.
                    	phase = Phase.breed_complete;
                    	newestOffspring = null;
                        timer.restart();
                    } else {
                    	// Create a new BoxedMorph from the next offspring
                    	newestOffspring = new BoxedMorph(boxes, youngerSib, boxes.midBox);
                    	if(vacantBoxNumber == boxes.midBox) {
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
