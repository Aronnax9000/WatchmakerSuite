package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Boxes;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphVector;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.swing.drawer.SwingMorphDrawer;

public abstract class BoxyMorphViewPanel extends SwingMorphViewPanelComponent {
	protected boolean showBoxes = true;
	protected MorphDrawer gd;
//	protected GraphicsDrawer gd = new MorphDrawer();
	
	private static final long serialVersionUID = -224189863008500654L;
	protected BoxedMorphVector boxedMorphVector = new BoxedMorphVector();
	protected Boxes boxes;
	public Boxes getBoxes() {
		return boxes;
	}
	public BoxedMorphVector getBoxedMorphVector() {
		return boxedMorphVector;
	}
	public void setBoxedMorphVector(BoxedMorphVector boxedMorphVector) {
		this.boxedMorphVector = boxedMorphVector;
	}

	public BoxyMorphViewPanel(AppData appData) {
		super(appData);
		gd = new SwingMorphDrawer(appData.getPhenotypeDrawer());
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension size = getSize();
        updateModel(size);
        if(showBoxes) 
        	appData.getBoxesDrawer().draw((Graphics2D)g, size, boxes);
        for(BoxedMorph boxedMorph: boxedMorphVector.getBoxedMorphs()) {
        	gd.setSize(boxes.getBoxSize(size));
        	gd.draw(boxedMorph, (Graphics2D) g);
        }
    }
    abstract protected void updateModel(Dimension size);
}