package net.richarddawkins.watchmaker.swing.components;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morphview.MorphViewComponent;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingBorderLayoutMorphView extends SwingMorphView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8993086892827289437L;
	protected MorphViewComponent centrePanel;
	protected MorphViewComponent lowerStrip;
	protected SwingAppData swingAppData;
	protected MorphViewComponent upperStrip;

	public SwingBorderLayoutMorphView(SwingAppData swingAppData) {
		super(swingAppData);
		this.setSwingAppData(swingAppData);
		this.setLayout(new BorderLayout());
	}

	public SwingBorderLayoutMorphView(SwingAppData swingAppData, String icon, String name) {
		this(swingAppData);
		this.setIcon(icon);
		this.setName(name);
	}

	public MorphViewComponent getCentrePanel() {
		return centrePanel;
	}

	public MorphViewComponent getLowerStrip() {
		return lowerStrip;
	}

	public SwingAppData getSwingAppData() {
		return swingAppData;
	}

	public MorphViewComponent getUpperStrip() {
		return upperStrip;
	}

	public void setCentrePanel(MorphViewComponent centrePanel) {
		if (this.centrePanel != null) {
			this.remove((JComponent) this.centrePanel);
		}
		this.centrePanel = centrePanel;
		this.add((JComponent) this.centrePanel, BorderLayout.CENTER);

	}

	public void setLowerStrip(MorphViewComponent lowerStrip) {
		if (this.lowerStrip != null)
			this.remove((JComponent) this.lowerStrip);
		this.lowerStrip = lowerStrip;
		if (this.lowerStrip != null)
			this.add((JComponent)this.lowerStrip, BorderLayout.PAGE_END);
	}

	protected void setSwingAppData(SwingAppData swingAppData) {
		this.swingAppData = swingAppData;
	}

	public void setUpperStrip(MorphViewComponent upperStrip) {
		if (this.upperStrip != null)
			this.remove((JPanel) this.upperStrip);
		this.upperStrip = upperStrip;
		if (this.upperStrip != null)
			this.add((JPanel) this.upperStrip, BorderLayout.PAGE_START);

	}
}
