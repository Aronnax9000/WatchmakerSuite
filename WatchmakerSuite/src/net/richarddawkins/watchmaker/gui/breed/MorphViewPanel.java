package net.richarddawkins.watchmaker.gui.breed;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morph.MorphConfig;

public class MorphViewPanel extends JPanel {

    protected MorphConfig config;
    
	public MorphConfig getConfig() {
        return config;
    }

    public void setConfig(MorphConfig config) {
        this.config = config;
    }

    /**
	 * 
	 */
	private static final long serialVersionUID = -6896360202157783971L;

	public MorphViewPanel(MorphConfig config) {
		super();
		this.config = config;
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