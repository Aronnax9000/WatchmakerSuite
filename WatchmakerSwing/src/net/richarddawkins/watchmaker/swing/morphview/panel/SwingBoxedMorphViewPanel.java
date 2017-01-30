package net.richarddawkins.watchmaker.swing.morphview.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphVector;
import net.richarddawkins.watchmaker.swing.SwingGeom;

public abstract class SwingBoxedMorphViewPanel extends SwingMorphViewPanel  {
	protected boolean showBoxes = true;

	@Override
	public Vector<Morph> getMorphs() {
		return boxedMorphVector.getMorphs();
	}

	private static final long serialVersionUID = -224189863008500654L;
	protected BoxedMorphVector boxedMorphVector = new BoxedMorphVector();
	protected BoxManager boxes;

	public BoxManager getBoxes() {
		return boxes;
	}

	public BoxedMorphVector getBoxedMorphVector() {
		return boxedMorphVector;
	}

	public void setBoxedMorphVector(BoxedMorphVector boxedMorphVector) {
		this.boxedMorphVector = boxedMorphVector;
	}

	public SwingBoxedMorphViewPanel(AppData appData) {
		super(appData);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		Dim size = SwingGeom.toWatchmakerDim(getSize());
		updateModel(size);
		if (showBoxes) {
			BoxesDrawer boxesDrawer = appData.getBoxesDrawer();
			boxesDrawer.draw(g2, size, boxes, boxedMorphVector.getBoxedMorphs().size() == 1);
		}
		for (BoxedMorph boxedMorph : boxedMorphVector.getBoxedMorphs()) {
			morphDrawer.setSize(boxes.getBoxSize(size));
			morphDrawer.draw(boxedMorph, g2, size);
		}
	}

	abstract protected void updateModel(Dim size);
}