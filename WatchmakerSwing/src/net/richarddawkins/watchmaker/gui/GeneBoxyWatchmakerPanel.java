package net.richarddawkins.watchmaker.gui;

public class GeneBoxyWatchmakerPanel extends WatchmakerPanel {


	private static final long serialVersionUID = 4916823549714431659L;

	public GeneBoxyWatchmakerPanel(SwingAppData swingAppData) {
		super(swingAppData);
	}

	public GeneBoxyWatchmakerPanel(
			SwingAppData swingAppData, String icon, 
			String name, boolean engineeringMode) {
		super(swingAppData, icon, name);
		setPageStartPanel(swingAppData.newGeneBoxStrip(engineeringMode));
	}

}