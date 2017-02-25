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
import net.richarddawkins.watchmaker.morphview.MorphViewWidget;
public class SwingSpeedSlider implements MorphViewWidget, ChangeListener {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.components.SwingScaleSlider");

	
	private static final long serialVersionUID = 1L;
	static final int SCALE_MIN = 0;
	static final int SCALE_MAX = +15;
	static final int SCALE_INIT = 2; // initial frames per second
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
        
		JSlider slider = new JSlider(JSlider.HORIZONTAL, SCALE_MIN, SCALE_MAX, SCALE_INIT);
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
			logger.info("New speed slider value: " + speed);
			appData.setTickDelay(Math.round(Math.pow(speed,2)));
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
