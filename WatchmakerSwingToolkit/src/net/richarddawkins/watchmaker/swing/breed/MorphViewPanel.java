package net.richarddawkins.watchmaker.swing.breed;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;

public class MorphViewPanel extends JPanel {

    protected SwingAppData swingAppData;


    /**
	 * 
	 */
	private static final long serialVersionUID = -6896360202157783971L;

	public MorphViewPanel(SwingAppData swingAppData) {
		super();
		this.swingAppData = swingAppData;
	}

	public SwingAppData getSwingAppData() {
		return swingAppData;
	}

	public void setSwingAppData(SwingAppData swingAppData) {
		this.swingAppData = swingAppData;
	}

	public MorphViewPanel(LayoutManager layout) {
		super(layout);
	}

	public MorphViewPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public MorphViewPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

}