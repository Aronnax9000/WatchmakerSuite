package net.richarddawkins.wm;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;

public class BreedingPanelOld extends JPanel {

	protected int rows = 0;
	protected int cols = 0;
	protected int midBox = 0;
	protected Vector<MorphPanel> morphPanels = new Vector<MorphPanel>();
	
	public int getMidBox() {
		return midBox;
	}

	public void setMidBox(int midBox) {
		this.midBox = midBox;
	}

	public Vector<MorphPanel> getMorphPanels() {
		return morphPanels;
	}

	public void setMorphPanels(Vector<MorphPanel> morphPanels) {
		this.morphPanels = morphPanels;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ComponentAdapter componentAdapter = new ComponentAdapter() {
    	public void componentResized(ComponentEvent e) {
    	  //FIXME
//    		Component c = e.getComponent();
//    		Dimension d = c.getSize();
//    		setPreferredSize(d);
//    		d = new Dimension(d.width / cols, d.height / rows);
//    		for(MorphPanel panel: morphPanels) {
//        		panel.setPreferredSize(d);
//    		}
//    		c.revalidate();
//    		c.repaint();
    	}
	};

	public void populate(Morph morph) {
        for(int i = 0; i < rows * cols; i++) {
        	Morph newMorph;
        	if(i == midBox)
        		newMorph = morph;
        	else
        		newMorph = morph.reproduce();
        	morphPanels.elementAt(i).setMorph(newMorph);
        }
        for(int i = 0; i < morphPanels.size(); i++) {
    		add(morphPanels.elementAt(i));
    	}
		repaint();
		revalidate();
	}
	
    public BreedingPanelOld(MorphConfig config)
    {
    	super();
    	setRows(config.getDefaultBreedingRows());
    	setCols(config.getDefaultBreedingCols());
    	GridLayout layout = new GridLayout(rows, cols);
        setLayout(layout);
    	setBorder(BorderFactory.createLineBorder(Color.black));
    	midBox = rows * cols / 2;
        for(int i = 0; i < rows * cols; i++) {
        	MorphPanel panel = new MorphPanel(this, i == midBox);
        	morphPanels.add(panel);
        }
        
        Morph morph = config.createMorph(1);
        populate(morph);
        config.getContainer().addComponentListener(componentAdapter);    
    }

//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);       
//    	g.clearRect(0, 0, getWidth(), getHeight());
//
//    }

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
		midBox = rows * cols / 2;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
		midBox = rows * cols / 2;
	}  
   
}
