package net.richarddawkins.watchmaker.gui.breed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import net.richarddawkins.watchmaker.drawer.BoxedMorph;
import net.richarddawkins.watchmaker.drawer.Boxes;
import net.richarddawkins.watchmaker.drawer.GraphicsDrawer;
import net.richarddawkins.watchmaker.drawer.MorphDrawer;
import net.richarddawkins.watchmaker.drawer.MorphDrawerOld;
import net.richarddawkins.watchmaker.gui.WatchmakerPanel;
import net.richarddawkins.watchmaker.gui.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.common.Morph;

public class BreedingPanel extends JPanel implements ActionListener {
	
	protected WatchmakerPanel watchmakerPanel;
	protected MouseAdapter mouseAdapter;
	private int cols = 5;	
	private int rows = 3;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8668997028542499649L;

	
	private Vector<GraphicsDrawer> thingsToDraw = new Vector<GraphicsDrawer>();	

    public Boxes boxesDrawer;

	
	public void add(GraphicsDrawer gd) { thingsToDraw.add(gd); }
	public void remove(GraphicsDrawer gd) { thingsToDraw.remove(gd); }
    
    
    
	public BreedingPanel(WatchmakerPanel watchmakerPanel) {
		this.watchmakerPanel = watchmakerPanel;
		boxesDrawer = new Boxes(cols,rows);
		mouseAdapter = new BreedingPanelMouseAdapter(this);
		this.addMouseMotionListener(new BreedingPanelMouseMotionAdapter(this));
		this.addMouseListener(mouseAdapter);
		setPreferredSize(new Dimension(640,480));
        setBorder(BorderFactory.createLineBorder(Color.black));
        Morph parent = watchmakerPanel.getMorphConfig().createMorph(1);
        boxesDrawer.getBoxedMorphs().add(new BoxedMorph(parent, cols * rows / 2));
        GeneBoxStrip geneBoxStrip = (GeneBoxStrip) watchmakerPanel.getPageStartPanel();
        geneBoxStrip.setGenome(parent.getGenome());
        
        special = cols * rows / 2;
        phase = Phase.mouse_clicked;

    }

	boolean showBoxes = true;
	
	enum Phase { breed_complete, mouse_clicked, animate_mother, reactivate_grid, draw_out_offspring };

	public Phase phase = Phase.breed_complete;
	public int special;
	Timer timer = new Timer(1000/60, this);
	
	private int vacantBoxNumber = -1;

	private MorphDrawer newestOffspring = null;
	private MorphDrawer momma = null;
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateModel();
        if(showBoxes) 
        	boxesDrawer.draw((Graphics2D)g, getSize());
        for(GraphicsDrawer gd: thingsToDraw) {
        	gd.setSize(boxesDrawer.getBoxSize(getSize()));
        	gd.draw((Graphics2D) g);
        }

    }
	private void updateModel() {
        Vector<Point> mids = boxesDrawer.getMidPoints(getSize());
    	
        switch(phase) {
        case breed_complete:
        	timer.stop();
            Iterator<Point> midPoints = mids.iterator();
            for(GraphicsDrawer gd: thingsToDraw) {
            	gd.setPosition(midPoints.next());
            }
            GeneBoxStrip geneBoxStrip = (GeneBoxStrip) watchmakerPanel.getPageStartPanel();
            geneBoxStrip.setGenome(boxesDrawer.getMorph(cols * rows / 2).getGenome());
            break;
        case mouse_clicked:
        	Point midPoint = mids.elementAt(special);
        	momma = new MorphDrawerOld(boxesDrawer.getMorph(special), midPoint);
        	momma.setPosition(midPoint);
        	momma.setDestination(mids.elementAt(cols * rows / 2));
        	momma.setProgress(0.0d);
        	momma.setScaleWithProgress(false);
        	thingsToDraw.removeAllElements();
        	thingsToDraw.add(momma);
        	showBoxes = false;
        	phase = Phase.animate_mother;
        	timer.start();
        	break;
        case animate_mother:
        	momma.setPosition(mids.elementAt(special));
        	momma.setDestination(mids.elementAt(cols * rows / 2));
        	momma.nudge();
        	if(momma.getProgress() == 1.0d)
        		phase = Phase.reactivate_grid;
        	break;
        case reactivate_grid:
        	timer.stop();
        	
        	momma.setPosition(mids.elementAt(rows * cols / 2));
        	momma.setDestination(null);
        	momma.setProgress(0.0d);
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
        			newestOffspring.setProgress(0.0d);
        			newestOffspring.setDestination(null);
        			newestOffspring = null;
        		}
        		break;
        	}
           	if(vacantBoxNumber == rows * cols) {
        		phase = Phase.breed_complete;
        		timer.restart();
        		break;
        	}
        	    
           	Morph babyMorph = momma.getMorph().reproduce();
           	boxesDrawer.getBoxedMorphs().add(new BoxedMorph(babyMorph, vacantBoxNumber));
            newestOffspring = new MorphDrawerOld(babyMorph, 
            		mids.elementAt(rows * cols / 2));
            newestOffspring.setPosition(momma.getPosition());
            newestOffspring.setDestination(mids.elementAt(vacantBoxNumber));
            newestOffspring.setProgress(0.0d);
            newestOffspring.setScaleWithProgress(true);
            thingsToDraw.add(vacantBoxNumber, newestOffspring);
    		
        	vacantBoxNumber++;
        	  if(vacantBoxNumber == rows * cols / 2)
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
