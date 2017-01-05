package net.richarddawkins.watchmaker.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morph.common.MorphConfig;

public class WatchmakerPanel extends MorphView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8993086892827289437L;
	protected JPanel pageStartPanel;
	protected JPanel centrePanel;
	protected MorphConfig config;

	public MorphConfig getMorphConfig() {
		return config;
	}

	public void setMorphConfig(MorphConfig config) {
		this.config = config;
	}

	public WatchmakerPanel(MorphConfig config) {
		this.setMorphConfig(config);
		this.setLayout(new BorderLayout());
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
