package net.richarddawkins.watchmaker.gui.breed;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;

import net.richarddawkins.watchmaker.drawer.BoxedMorph;
import net.richarddawkins.watchmaker.drawer.Boxes;
import net.richarddawkins.watchmaker.drawer.GraphicsDrawer;
import net.richarddawkins.watchmaker.drawer.MorphDrawerOld;

public abstract class BoxyMorphViewPanel extends MorphViewPanel {
	protected boolean showBoxes = true;
	protected GraphicsDrawer gd = new MorphDrawerOld();
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

	public BoxyMorphViewPanel() {
		super();
	}

	public BoxyMorphViewPanel(LayoutManager layout) {
		super(layout);
	}

	public BoxyMorphViewPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public BoxyMorphViewPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
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