package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.BorderLayout;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.morphview.MorphViewWidget;

public class SwingBorderLayoutMorphView extends SwingMorphView {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.components.SwingBorderLayoutMorphView");
	private static final long serialVersionUID = 8993086892827289437L;
	protected MorphViewPanel centrePanel;
	protected MorphViewWidget lowerStrip;

	protected MorphViewWidget upperStrip;

	public SwingBorderLayoutMorphView(AppData appData) {
		super(appData);
		this.setLayout(new BorderLayout());
	}

	public SwingBorderLayoutMorphView(AppData appData, String icon, String name) {
		this(appData);
		this.setIcon(icon);
		this.setName(name);
	}

	public MorphViewPanel getCentrePanel() {
		return centrePanel;
	}

	public MorphViewWidget getLowerStrip() {
		return lowerStrip;
	}

	public MorphViewWidget getUpperStrip() {
		return upperStrip;
	}

	public void setCentrePanel(MorphViewPanel centrePanel) {
		if (this.centrePanel != null) {
			this.remove((JComponent) this.centrePanel);
		}
		this.centrePanel = centrePanel;
		this.add((JComponent) this.centrePanel, BorderLayout.CENTER);
	}

	public void setLowerStrip(MorphViewWidget lowerStrip) {
		if (this.lowerStrip != null)
			this.remove((JComponent) this.lowerStrip);
		this.lowerStrip = lowerStrip;
		if (this.lowerStrip != null)
			this.add((JComponent)this.lowerStrip, BorderLayout.PAGE_END);
	}

	public void setUpperStrip(MorphViewWidget upperStrip) {
		if (this.upperStrip != null)
			this.remove((JPanel) this.upperStrip);
		this.upperStrip = upperStrip;
		if (this.upperStrip != null)
			this.add((JPanel) this.upperStrip, BorderLayout.PAGE_START);

	}

	@Override
	public MorphViewPanel getMorphViewPanel() {
		return centrePanel;
	}
}
