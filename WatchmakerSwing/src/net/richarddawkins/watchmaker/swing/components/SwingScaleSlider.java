package net.richarddawkins.watchmaker.swing.components;

import java.util.logging.Logger;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.morphview.MorphViewWidget;
import net.richarddawkins.watchmaker.phenotype.DrawingPreferences;

public class SwingScaleSlider extends JSlider implements MorphViewWidget, ChangeListener {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.components.SwingScaleSlider");

	private static final long serialVersionUID = 1L;
	static final int SCALE_MIN = -8;
	static final int SCALE_MAX = +8;
	static final int SCALE_INIT = 0; // initial frames per second
	protected DrawingPreferences drawingPreferences;
	public SwingScaleSlider(DrawingPreferences drawingPreferences) {

		super(JSlider.HORIZONTAL, SCALE_MIN, SCALE_MAX, SCALE_INIT);
		this.drawingPreferences = drawingPreferences;
		this.addChangeListener(this);
		// Turn on labels at major tick marks.
		setMajorTickSpacing(1);
		setPaintTicks(true);
		setPaintLabels(true);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(! ((JSlider)e.getSource()).getValueIsAdjusting()) {
			int scale = getValue();
			logger.info("New scale slider value: " + scale);
			drawingPreferences.setScale(scale);
		}
		
	}
}
