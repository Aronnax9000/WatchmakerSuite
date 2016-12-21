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

import net.richarddawkins.watchmaker.draw.QuickDrawState;
import net.richarddawkins.watchmaker.drawer.Boxes;
import net.richarddawkins.watchmaker.drawer.GraphicsDrawer;
import net.richarddawkins.watchmaker.drawer.MorphDrawer;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;

public class BreedingPanel extends JPanel implements ActionListener {
	
	private MorphConfig morphConfig;
	private MouseAdapter mouseAdapter;
	private int cols = 5;	
	private int rows = 3;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8668997028542499649L;

	private QuickDrawState quickDrawState = new QuickDrawState();

	private Vector<GraphicsDrawer> thingsToDraw = new Vector<GraphicsDrawer>();	

    public Boxes boxesDrawer;

	
	public void add(GraphicsDrawer gd) { thingsToDraw.add(gd); }
	public void remove(GraphicsDrawer gd) { thingsToDraw.remove(gd); }
    private Vector<Morph> morphs = new Vector<Morph>();
	
    
    
	public BreedingPanel(MorphConfig morphConfig) {
		this.morphConfig = morphConfig;
		boxesDrawer = new Boxes(cols,rows);
		mouseAdapter = new BreedingPanelMouseAdapter(this);
		this.addMouseListener(mouseAdapter);
		setPreferredSize(new Dimension(640,480));
        setBorder(BorderFactory.createLineBorder(Color.black));
        Morph parent = morphConfig.createMorph(1);
        int  midBox = cols * rows / 2;
        for(int i = 0; i < cols * rows; i++) {
        	if(i == midBox)
        		morphs.add(parent);
        	else
        		morphs.add(parent.reproduce());
        }
        
        Iterator<Point> midPoints = boxesDrawer.getMidPoints(getSize()).iterator();
        for(Morph m1: morphs) {
        	thingsToDraw.add(new MorphDrawer(m1, midPoints.next()));
        }        
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
        	gd.draw((Graphics2D) g);
        }
        
        // Draw Text
        //g.drawString("This is my custom Panel!",10,20);
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
            morphConfig.getGeneBoxStrip()
            .setGenome(((MorphDrawer)thingsToDraw.elementAt(cols * rows / 2)).getMorph().getGenome());
            break;
        case mouse_clicked:
        	momma = (MorphDrawer) thingsToDraw.elementAt(special);
        	Point midPoint = mids.elementAt(special);
        	momma.setPosition(midPoint);
        	momma.setDestination(mids.elementAt(cols * rows / 2));
        	momma.setProgress(0.0d);
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
        			newestOffspring = null;
        		}
        		break;
        	}
           	if(vacantBoxNumber == rows * cols) {
        		phase = Phase.breed_complete;
        		timer.restart();
        		break;
        	}
        	    
            newestOffspring = new MorphDrawer(momma.getMorph().reproduce(), 
            		mids.elementAt(rows * cols / 2));
            newestOffspring.setPosition(momma.getPosition());
            newestOffspring.setDestination(mids.elementAt(vacantBoxNumber));
            newestOffspring.setProgress(0.0d);
            thingsToDraw.add(vacantBoxNumber, newestOffspring);
    		
        	vacantBoxNumber++;
        	  if(vacantBoxNumber == rows * cols / 2)
        		  vacantBoxNumber++;
        	timer.restart();
    	    break;        
    	
        default:
        }
		
	}
	public QuickDrawState getQuickDrawState() {
		return quickDrawState;
	}
	public void setQuickDrawState(QuickDrawState quickDrawState) {
		this.quickDrawState = quickDrawState;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.repaint();
		
	}
}
