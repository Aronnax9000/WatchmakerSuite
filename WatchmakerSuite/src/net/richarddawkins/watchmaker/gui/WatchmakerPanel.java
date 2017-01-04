package net.richarddawkins.watchmaker.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class WatchmakerPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8993086892827289437L;
	private JPanel pageStartPanel;
	private JPanel centrePanel;
	
	public WatchmakerPanel(JPanel pageStartPanel, JPanel centrePanel) {
		this.setLayout(new BorderLayout());
		this.setPageStartPanel(pageStartPanel);
		this.setCentrePanel(centrePanel); 
	}

	public JPanel getPageStartPanel() {
		return pageStartPanel;
	}

	public void setPageStartPanel(JPanel pageStartPanel) {
		if(this.pageStartPanel != null)
			this.remove(this.pageStartPanel);
		this.pageStartPanel = pageStartPanel;
		if(this.pageStartPanel != null)
			this.add(this.pageStartPanel, BorderLayout.PAGE_START);
		
	}

	public JPanel getCentrePanel() {
		return centrePanel;
	}

	public void setCentrePanel(JPanel centrePanel) {
		if(this.centrePanel != null) {
			this.remove(this.centrePanel);
			this.centrePanel = centrePanel;
			this.add(this.centrePanel, BorderLayout.CENTER);
		}
	}
}
