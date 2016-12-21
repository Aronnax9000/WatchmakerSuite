package net.richarddawkins.watchmaker.gui.genebox;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morph.mono.SwellType;
import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;



public class GradientPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 273011307434890144L;
	public void setSwell(SwellType swell) {
		this.removeAll();
		GridBagConstraints constraints = new GridBagConstraints();
		switch(swell) {
		case Shrink:
			constraints.anchor = GridBagConstraints.PAGE_END;
			this.add(icon, constraints);
			break;
		case Same:
			break;
		case Swell:
			constraints.anchor = GridBagConstraints.PAGE_START;
			this.add(icon, constraints);
			break;
			
		}
	}
	
	private JLabel icon = new JLabel(
			new ImageIcon(
					ClassicImageLoader
						.getPicture("BulletChar165_ALAN_0015_5x5")
				.getImage()
				));
	public GradientPanel() {
		this.setLayout(new GridBagLayout());
	}
	
	
	
	  
}
