package net.richarddawkins.watchmaker.morphview;

import java.util.Vector;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.component.WatchComponent;

public interface MorphViewsTabbedPanel extends WatchComponent  {

	void addMorphView(MorphView morphView);

//	MorphView getSelectedMorphView();

	AppData getAppData();

	void setAppData(AppData appData);

    Vector<MorphView> getMorphViews();
	

}