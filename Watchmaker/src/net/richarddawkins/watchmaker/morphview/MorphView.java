package net.richarddawkins.watchmaker.morphview;

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
	void setShowBoxes(boolean showBoxes);
	void setMorphDrawer(MorphDrawer morphDrawer);
	
	void setBoxes(BoxManager boxes);
	void setBoxedMorphVector(BoxedMorphCollection boxedMorphVector);
	void setAppData(AppData appData);
	boolean isShowBoxes();
	MorphDrawer getMorphDrawer();
	JPanel getCentrePanel();
	BoxManager getBoxes();
	BoxedMorphCollection getBoxedMorphVector();
	void paintMorphViewPanel(Object graphicsContext, Dim size);
}