package net.richarddawkins.watchmaker.swing.breed;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Boxes;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.drawer.GraphicsDrawer;
import net.richarddawkins.watchmaker.swing.drawer.MorphDrawerOld;

public abstract class BoxyMorphViewPanel extends SwingMorphViewPanelComponent {
	protected boolean showBoxes = true;
	protected GraphicsDrawer gd;
//	protected GraphicsDrawer gd = new MorphDrawer();
	
	private static final long serialVersionUID = -224189863008500654L;
	protected BoxedMorphVector boxedMorphVector = new BoxedMorphVector();
	protected Boxes boxesDrawer;
	public Boxes getBoxesDrawer() {
		return boxesDrawer;
	}
	public BoxedMorphVector getBoxedMorphVector() {
		return boxedMorphVector;
	}
	public void setBoxedMorphVector(BoxedMorphVector boxedMorphVector) {
		this.boxedMorphVector = boxedMorphVector;
	}

	public BoxyMorphViewPanel(SwingAppData swingAppData) {
		super(swingAppData);
		gd = new MorphDrawerOld(swingAppData.getSwingPicDrawer());
	}



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = getSize();
        updateModel(size);
        if(showBoxes) 
        	boxesDrawer.draw((Graphics2D)g, size);
        for(BoxedMorph boxedMorph: boxedMorphVector.boxedMorphs) {
        	gd.setSize(boxesDrawer.getBoxSize(size));
        	gd.draw(boxedMorph, (Graphics2D) g);
        }
    }
    abstract protected void updateModel(Dimension size);
}