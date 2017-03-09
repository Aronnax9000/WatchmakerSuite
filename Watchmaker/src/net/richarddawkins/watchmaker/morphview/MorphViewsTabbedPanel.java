package net.richarddawkins.watchmaker.morphview;

import java.util.Vector;

import net.richarddawkins.watchmaker.app.AppData;

public interface MorphViewsTabbedPanel {

	void addMorphView(MorphView morphView);

//	MorphView getSelectedMorphView();

	AppData getAppData();

	void setAppData(AppData appData);

    Vector<MorphView> getMorphViews();
	

}