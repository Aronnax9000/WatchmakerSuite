package net.richarddawkins.watchmaker.gui.breed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Timer;

import net.richarddawkins.watchmaker.drawer.BoxedMorph;
import net.richarddawkins.watchmaker.drawer.Boxes;
import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.WatchmakerPanel;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public class BreedingPanel extends BoxyMorphViewPanel implements ActionListener {
	
	private static final long serialVersionUID = 8668997028542499649L;
	protected WatchmakerPanel watchmakerPanel;
	protected MouseAdapter mouseAdapter;


	
	enum Phase { breed_complete, mouse_clicked, animate_mother, reactivate_grid, draw_out_offspring };

	public Phase phase = Phase.breed_complete;
	public int special = -1;
	protected BoxedMorph boxedMorphSpecial;
	Timer timer = new Timer(1000/60, this);
	
	private int vacantBoxNumber = -1;

	private BoxedMorph newestOffspring = null;
 
	

	public void seed(Morph morph) {
		
        Morph parent;
        if(morph == null) 
        	parent = watchmakerPanel.getMorphConfig().createMorph(1);
        else
        	parent = morph;
        BoxedMorph boxedMorph = new BoxedMorph(parent, boxesDrawer.midBox); 
        boxedMorphVector.add(boxedMorph);
        GeneBoxStrip geneBoxStrip = (GeneBoxStrip) watchmakerPanel.getPageStartPanel();
        geneBoxStrip.setGenome(parent.getGenome());
        // Trigger first breeding
        special = boxesDrawer.midBox;
        phase = Phase.mouse_clicked;

	}


	public BreedingPanel(WatchmakerPanel watchmakerPanel) {
		this.watchmakerPanel = watchmakerPanel;
		MorphConfig config = watchmakerPanel.getMorphConfig();
		boxesDrawer = new Boxes(config.getDefaultBreedingCols(),
				config.getDefaultBreedingRows());
		mouseAdapter = new BreedingPanelMouseAdapter(this);
		this.addMouseMotionListener(new BreedingPanelMouseMotionAdapter(this));
		this.addMouseListener(mouseAdapter);
		setPreferredSize(new Dimension(640,480));
        setBorder(BorderFactory.createLineBorder(Color.black));
        
    }



	protected void updateModel(Dimension size) {
        Vector<Point> mids = boxesDrawer.getMidPoints(size);
    	
        switch(phase) {
        case breed_complete:
        	special = -1;
        	boxedMorphSpecial = null;
        	timer.stop();
        	
            for(BoxedMorph boxedMorph: boxedMorphVector.getBoxedMorphs()) {
            	boxedMorph.setPosition(boxesDrawer.getMidPoint(size, boxedMorph.boxNo));
            }

            GeneBoxStrip geneBoxStrip = (GeneBoxStrip) watchmakerPanel.getPageStartPanel();
            geneBoxStrip.setGenome(boxedMorphVector.getBoxedMorph(boxesDrawer.midBox).getMorph().getGenome());
            break;
        case mouse_clicked:
        	boxedMorphSpecial = boxedMorphVector.getBoxedMorph(special);
        	boxedMorphVector.removeAllElements();
        	boxedMorphVector.add(boxedMorphSpecial);
        	Point midPoint = boxesDrawer.getMidPoint(size, special);
        	boxedMorphSpecial.setPosition(midPoint);
        	boxedMorphSpecial.setDestination(mids.elementAt(boxesDrawer.midBox));
        	boxedMorphSpecial.setProgress(0.0d);
        	boxedMorphSpecial.setScaleWithProgress(false);
        	showBoxes = false;
        	phase = Phase.animate_mother;
        	timer.start();
        	break;
        case animate_mother:
        	boxedMorphSpecial.setPosition(mids.elementAt(special));
        	boxedMorphSpecial.setDestination(mids.elementAt(boxesDrawer.midBox));
        	boxedMorphSpecial.nudge();
        	if(boxedMorphSpecial.getProgress() == 1.0d)
        		phase = Phase.reactivate_grid;
        	break;
        case reactivate_grid:
        	timer.stop();
        	boxedMorphSpecial.setDestination(null);
        	boxedMorphSpecial.setProgress(0.0d);
        	boxedMorphSpecial.setBoxNo(boxesDrawer.midBox);
        	boxedMorphSpecial.setPosition(boxesDrawer.getMidPoint(size, boxesDrawer.midBox));
        	showBoxes = true;
        	vacantBoxNumber = 0;
        	phase = Phase.draw_out_offspring;
        	timer.start();
        	break;
        case draw_out_offspring:
        	if(newestOffspring != null) {
        		newestOffspring.nudge();
        		if(newestOffspring.getProgress() == 1.0d) {
        			newestOffspring.setPosition(newestOffspring.getDestination());
        			newestOffspring.setDestination(null);
        			newestOffspring.setProgress(0.0d);
        			newestOffspring = null;
        		}
        		break;
        	}
           	if(vacantBoxNumber == boxesDrawer.boxCount) {
        		phase = Phase.breed_complete;
        		timer.restart();
        		break;
        	}
        	
           	Morph babyMorph = boxedMorphSpecial.getMorph().reproduce();
           	newestOffspring = new BoxedMorph(babyMorph, vacantBoxNumber);
           	newestOffspring.setPosition(boxedMorphSpecial.getPosition());
           	newestOffspring.setDestination(mids.elementAt(vacantBoxNumber));
           	newestOffspring.setProgress(0.0d);
           	newestOffspring.setScaleWithProgress(true);
           	boxedMorphVector.add(newestOffspring);

           	vacantBoxNumber++;
        	// Skip center box number (already occupied by mother)
           	if(vacantBoxNumber == boxesDrawer.midBox)
        		  vacantBoxNumber++;
        	 
        	timer.restart();
    	    break;        
    	
        default:
        }
		
	}

	public WatchmakerPanel getWatchmakerPanel() {
		return watchmakerPanel;
	}
	public void setWatchmakerPanel(WatchmakerPanel watchmakerPanel) {
		this.watchmakerPanel = watchmakerPanel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.repaint();
	}

}
