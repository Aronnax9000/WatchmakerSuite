package net.richarddawkins.watchmaker.morph.arthro;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ArthromorphPreferences extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ArthromorphConfig config;
	
	JCheckBox wantColor = new JCheckBox("Color");
	JCheckBox sideways = new JCheckBox("Sideways");
	JCheckBox centring = new JCheckBox("Centring");
	JTextField rows = new JTextField(2);
	JTextField cols = new JTextField(2);
	
	
	public ArthromorphPreferences(ArthromorphConfig newConfig) {
		super("Preferences");
		this.config = newConfig;
		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				wantColor.setSelected(config.isWantColor());
				sideways.setSelected(config.isSideways());
				centring.setSelected(config.isCentring());
//				rows.setText(new Integer(config.getBreedingAndGeneBoxPanel().getBreedingPanel().getRows()).toString());
//				cols.setText(new Integer(config.getBreedingAndGeneBoxPanel().getBreedingPanel().getCols()).toString());
			}
			});
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(wantColor, constraints );
		constraints.gridx = 1;
		panel.add(sideways, constraints);
		constraints.gridx = 2;
		panel.add(centring, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		panel.add(new JLabel("How Many Rows"), constraints);
		constraints.gridx = 1;
		panel.add(rows, constraints);
		constraints.gridx = 0;
		constraints.gridy = 3;		
		panel.add(new JLabel("How Many Columns"), constraints);
		constraints.gridx = 1;
		panel.add(cols, constraints);
		constraints.gridy = 4;
		constraints.gridx = 0;
		constraints.gridwidth = 3;
		panel.add(new JButton(new AbstractAction("OK") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				handleOKButton();
			}
			
		}), constraints);
		this.getContentPane().add(panel);
		this.pack();
		
	}
	public void handleOKButton() {
	/*	boolean agreeToExit = true;
		if(agreeToExit) {
			config.setWantColor(wantColor.isSelected());
			config.setSideways(sideways.isSelected());
			config.setCentring(centring.isSelected());
			BreedingPanel breedingPanel = config.getBreedingAndGeneBoxPanel().getBreedingPanel();
			breedingPanel.setRows(Integer.parseInt(rows.getText()));
			breedingPanel.setCols(Integer.parseInt(cols.getText()));
			this.dispose();
		}
		*/
	}
}
