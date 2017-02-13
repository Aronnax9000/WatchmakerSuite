package net.richarddawkins.watchmaker.swing.zoo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.MultiMorphTypeTabbedPanel;
import net.richarddawkins.watchmaker.app.WatchmakerTabComponent;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;

public class SwingMultiMorphTypeTabComponent extends JPanel implements WatchmakerTabComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5284721564837850902L;
	protected MultiMorphTypeTabbedPanel pane;
	protected JLabel lblTitle;
	public MultiMorphTypeTabbedPanel getPane() {
		return pane;
	}

	public void setPane(MultiMorphTypeTabbedPanel pane) {
		this.pane = pane;
	}

	public void setName(String name) {
		lblTitle.setText(name);
		btnClose.addActionListener(new MyCloseActionHandler(name));
	}
	
	protected void setIconFromFilename(String filename) {
		lblIcon.setIcon(new ImageIcon(ClassicImageLoader.getPicture(filename).getImage()));
	}
	
	JButton btnClose = null;
	JLabel lblIcon = null;
	public SwingMultiMorphTypeTabComponent() {
		setLayout(new GridBagLayout());
		setOpaque(false);
		lblTitle = new JLabel();
		btnClose = new JButton("x");
		btnClose.setMargin(new Insets(0,0,0,0));
		
		lblIcon = new JLabel();
		

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.weightx = 1;
		add(lblIcon, gbc);

		gbc.gridx++;
		gbc.weightx = 2;
		add(lblTitle, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		gbc.weighty = 0;
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
	        int index = pane.indexOfTab(getTabName());
	        if (index >= 0) {
	            pane.removeTabAt(index);
	            ((JButton)evt.getSource()).removeActionListener(this);
	        }

	    }

	}

	public Icon getIcon() {
		return lblIcon.getIcon();
	}   
}
