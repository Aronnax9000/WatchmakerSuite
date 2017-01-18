package net.richarddawkins.watchmaker.swing.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.morphview.MorphView;

public class WatchmakerPanel extends MorphView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8993086892827289437L;
	protected JPanel pageStartPanel;
	protected JPanel centrePanel;
	protected SwingAppData swingAppData;

	public SwingAppData getSwingAppData() {
		return swingAppData;
	}
	public WatchmakerPanel(SwingAppData swingAppData) {
		super(swingAppData);
		this.setSwingAppData(swingAppData);
		this.setLayout(new BorderLayout());
	}
	protected void setSwingAppData(SwingAppData swingAppData) {
		this.swingAppData = swingAppData;
	}

	public WatchmakerPanel(SwingAppData swingAppData, String icon, String name) {
       	this(swingAppData);
       	this.setIconFromFilename(icon);
       	this.setName(name);
	}
	
	public JPanel getPageStartPanel() {
		return pageStartPanel;
	}

	public void setPageStartPanel(JPanel pageStartPanel) {
		if (this.pageStartPanel != null)
			this.remove(this.pageStartPanel);
		this.pageStartPanel = pageStartPanel;
		if (this.pageStartPanel != null)
			this.add(this.pageStartPanel, BorderLayout.PAGE_START);

	}

	public JPanel getCentrePanel() {
		return centrePanel;
	}

	public void setCentrePanel(JPanel centrePanel) {
		if (this.centrePanel != null) {
			this.remove(this.centrePanel);
		}
		this.centrePanel = centrePanel;
		this.add(this.centrePanel, BorderLayout.CENTER);
		
	}
}
