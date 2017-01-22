package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morphview.MorphViewComponent;

public class SwingMorphViewPanelComponent extends JPanel implements MorphViewComponent{

    protected AppData appData;


    /**
	 * 
	 */
	private static final long serialVersionUID = -6896360202157783971L;

	public SwingMorphViewPanelComponent(AppData appData) {
		super();
		this.appData = appData;
	}

	public AppData getAppData() {
		return appData;
	}

	public void setAppData(AppData appData) {
		this.appData = appData;
	}

	public SwingMorphViewPanelComponent(LayoutManager layout) {
		super(layout);
	}

	public SwingMorphViewPanelComponent(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public SwingMorphViewPanelComponent(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

}