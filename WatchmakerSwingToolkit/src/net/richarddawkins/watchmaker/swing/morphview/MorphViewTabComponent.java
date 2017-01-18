package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MorphViewTabComponent extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5284721564837850902L;
	protected MorphViewsTabbedPane pane;
	public MorphViewTabComponent(MorphViewsTabbedPane pane, Icon icon, String name) {
		this.pane = pane;
	
		setLayout(new GridBagLayout());
		setOpaque(false);
		JLabel lblTitle = new JLabel(name);
		JButton btnClose = new JButton("x");
		

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridy = 0;
		gbc.gridx = 0;
		add(new JLabel(icon), gbc);

		gbc.gridx++;
		add(lblTitle, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		add(btnClose, gbc);

		
		btnClose.addActionListener(new MyCloseActionHandler(name));
		
	}
	
	public class MyCloseActionHandler implements ActionListener {

	    private String tabName;

	    public MyCloseActionHandler(String tabName) {
	        this.tabName = tabName;
	    }

	    public String getTabName() {
	        return tabName;
	    }

	    public void actionPerformed(ActionEvent evt) {
	        int index = pane.indexOfTab(getTabName());
	        if (index >= 0) {
	            pane.removeTabAt(index);
	            ((JButton)evt.getSource()).removeActionListener(this);
	        }

	    }

	}   
}
