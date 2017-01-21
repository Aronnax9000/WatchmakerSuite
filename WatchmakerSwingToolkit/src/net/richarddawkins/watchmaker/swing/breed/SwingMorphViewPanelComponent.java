package net.richarddawkins.watchmaker.swing.breed;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morphview.MorphViewComponent;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;

public class SwingMorphViewPanelComponent extends JPanel implements MorphViewComponent{

    protected SwingAppData swingAppData;


    /**
	 * 
	 */
	private static final long serialVersionUID = -6896360202157783971L;

	public SwingMorphViewPanelComponent(SwingAppData swingAppData) {
		super();
		this.swingAppData = swingAppData;
	}

	public SwingAppData getSwingAppData() {
		return swingAppData;
	}

	public void setSwingAppData(SwingAppData swingAppData) {
		this.swingAppData = swingAppData;
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