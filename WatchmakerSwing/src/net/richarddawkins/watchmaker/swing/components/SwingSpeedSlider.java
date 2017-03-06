package net.richarddawkins.watchmaker.swing.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morphview.SpeedSlider;
public class SwingSpeedSlider implements SpeedSlider, ChangeListener {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.components.SwingScaleSlider");

	
	private static final long serialVersionUID = 1L;
	static final int SCALE_MIN = 0;
	static final int SCALE_MAX = +5;
	protected AppData appData;
	public SwingSpeedSlider(AppData appData) {
        this.appData = appData;
        this.panel = new JPanel();
        panel.setLayout(new GridBagLayout());
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.gridx = 0;
	    constraints.gridy = 0;
	    JLabel label = new JLabel("Tick Delay (2^n)");
	    panel.add(label, constraints);
	    constraints.gridy = 1;
        int speed = (int) Math.round(Math.log(appData.getTickDelay())/Math.log(2));
		JSlider slider = new JSlider(JSlider.HORIZONTAL, SCALE_MIN, SCALE_MAX, speed);
		slider.addChangeListener(this);
		// Turn on labels at major tick marks.
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		panel.add(slider,constraints);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
	    JSlider slider = (JSlider) e.getSource();
		if(! slider.getValueIsAdjusting()) {
			int speed = slider.getValue();
			long tickDelay = Math.round(Math.pow(2,speed));
            logger.info("New speed slider value: " + speed + " -> tickDelay: " + tickDelay);
			appData.setTickDelay(tickDelay);
		}
		
	}

	protected JPanel panel;
	
	@Override
	public JPanel getPanel() {
		return panel;
	}

    @Override
    public void setPanel(Object newValue) {
        panel = (JPanel) newValue;
        
    }

}
