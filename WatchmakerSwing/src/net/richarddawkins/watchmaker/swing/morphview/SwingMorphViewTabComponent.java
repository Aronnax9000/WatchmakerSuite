package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morphview.MorphViewTabComponent;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;

public class SwingMorphViewTabComponent extends JPanel implements MorphViewTabComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5284721564837850902L;
	

	
	public void setIcon(String filename) {
		lblIcon.setIcon(new ImageIcon(ClassicImageLoader.getPicture(filename).getImage()));
	}
	
	public void setName(String name) {
		lblTitle.setText(name);
		btnClose.addActionListener(new MyCloseActionHandler(name));
	}
	
	JLabel lblTitle = new JLabel();
	JButton btnClose = new JButton("x");
	JLabel lblIcon = new JLabel();

	
	public SwingMorphViewTabComponent() {
		
	
		setLayout(new GridBagLayout());
		setOpaque(false);
		
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridy = 0;
		gbc.gridx = 0;
		add(lblIcon, gbc);

		gbc.gridx++;
		add(lblTitle, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		add(btnClose, gbc);
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
	        int index = swingMorphViewsTabbedPanel.indexOfTab(getTabName());
	        if (index >= 0) {
	        	swingMorphViewsTabbedPanel.removeTabAt(index);
	            ((JButton)evt.getSource()).removeActionListener(this);
	        }

	    }

	}
	SwingMorphViewsTabbedPanel swingMorphViewsTabbedPanel;

	public SwingMorphViewsTabbedPanel getSwingMorphViewsTabbedPanel() {
		return swingMorphViewsTabbedPanel;
	}

	public void setSwingMorphViewsTabbedPanel(SwingMorphViewsTabbedPanel swingMorphViewsTabbedPanel) {
		this.swingMorphViewsTabbedPanel = swingMorphViewsTabbedPanel;
	}

}
