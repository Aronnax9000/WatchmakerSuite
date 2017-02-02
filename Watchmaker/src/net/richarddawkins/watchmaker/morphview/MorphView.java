package net.richarddawkins.watchmaker.morphview;

import java.awt.Graphics2D;
import java.util.Vector;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;

public interface MorphView {

	String getIcon();
	void setIcon(String icon);

	AppData getAppData();

	String getToolTip();

	void setToolTip(String toolTip);
	void seed(Morph morph);
	Morph getMorphOfTheHour();
	String getName();
	void setName(String newName);
	Vector<Morph> getMorphs();
	void paintMorphViewPanel(Graphics2D g2, Dim size);
	void setUpperStrip(MorphViewWidget upperStrip);
	void setShowBoxes(boolean showBoxes);
	void setMorphDrawer(MorphDrawer morphDrawer);
	void setLowerStrip(MorphViewWidget lowerStrip);
	void setBoxes(BoxManager boxes);
	void setBoxedMorphVector(BoxedMorphCollection boxedMorphVector);
	void setAppData(AppData appData);
	boolean isShowBoxes();
	MorphViewWidget getUpperStrip();
	MorphDrawer getMorphDrawer();
	MorphViewWidget getLowerStrip();
	JPanel getCentrePanel();
	BoxManager getBoxes();
	BoxedMorphCollection getBoxedMorphVector();
}