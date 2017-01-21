package net.richarddawkins.watchmaker.swing.morphview;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;

public class SwingMorphView extends JPanel implements MorphView {
	private static final long serialVersionUID = 5555392236002752598L;
	protected String icon;

	public void setIcon(String icon) {
		this.icon = icon;
	}

	protected SwingAppData swingAppData;
	protected String toolTip;

	public SwingMorphView(SwingAppData swingAppData) {
		this.swingAppData = swingAppData;
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
}
