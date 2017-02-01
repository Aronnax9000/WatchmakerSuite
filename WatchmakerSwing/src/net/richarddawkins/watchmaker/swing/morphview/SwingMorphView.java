package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.morphview.MorphViewWidget;
import net.richarddawkins.watchmaker.swing.components.SwingScaleSlider;

public class SwingMorphView extends JPanel implements MorphView {
	private static final long serialVersionUID = 5555392236002752598L;
	protected String icon;

	public void setIcon(String icon) {
		this.icon = icon;
	}

	protected AppData appData;
	public AppData getAppData() {
		return appData;
	}

	protected String toolTip;

	public SwingMorphView(AppData appData) {
		this.appData = appData;
		this.setLayout(new BorderLayout());
	}

	public SwingMorphView(AppData appData, String icon, String name) {
		this(appData);
		this.setIcon(icon);
		this.setName(name);
		
	}
	
	public SwingMorphView(
			AppData appData, String icon, 
			String name, boolean engineeringMode) {
		this(appData, icon, name);
		GeneBoxStrip geneBoxStrip = appData.newGeneBoxStrip(engineeringMode);
		setUpperStrip(geneBoxStrip);
		SwingScaleSlider scaleSlider = new SwingScaleSlider(appData.getPhenotypeDrawer().getDrawingPreferences());
		setLowerStrip(scaleSlider);
	}
	
	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public Morph getMorphOfTheHour() {
		return null;
	}

	@Override
	public String getToolTip() {
		return toolTip;
	}

	@Override
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	protected MorphViewPanel centrePanel;
	protected MorphViewWidget lowerStrip;
	protected MorphViewWidget upperStrip;


	
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
