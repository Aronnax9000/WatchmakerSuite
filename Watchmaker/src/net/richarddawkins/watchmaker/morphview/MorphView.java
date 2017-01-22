package net.richarddawkins.watchmaker.morphview;

import net.richarddawkins.watchmaker.morph.Morph;

public interface MorphView {

	String getIcon();
	void setIcon(String icon);


	String getToolTip();

	void setToolTip(String toolTip);

	Morph getMorphOfTheHour();
	String getName();
	void setName(String newName);


}