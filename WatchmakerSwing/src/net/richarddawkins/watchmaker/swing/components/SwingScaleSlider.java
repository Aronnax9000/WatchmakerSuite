package net.richarddawkins.watchmaker.swing.components;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.morphview.MorphViewComponent;
import net.richarddawkins.watchmaker.scale.WatchmakerScaleListener;
import net.richarddawkins.watchmaker.scale.WatchmakerScaleSupport;

public class SwingScaleSlider extends JSlider implements MorphViewComponent {
	WatchmakerScaleSupport watchmakerScaleSupport = new WatchmakerScaleSupport(this);
	private static final long serialVersionUID = 1L;
	static final int SCALE_MIN = -8;
	static final int SCALE_MAX = +8;
	static final int SCALE_INIT = 0; // initial frames per second

	public SwingScaleSlider() {

		super(JSlider.HORIZONTAL, SCALE_MIN, SCALE_MAX, SCALE_INIT);

		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				watchmakerScaleSupport.fireWatchmakerScaleEvent(getValue());

			}
		});

		// Turn on labels at major tick marks.
		setMajorTickSpacing(1);
		setPaintTicks(true);
		setPaintLabels(true);
	}
		
	public void addWatchmakerScaleListener(WatchmakerScaleListener listener){
		watchmakerScaleSupport.addWatchmakerScaleListener(listener);
	}
	public void removeWatchmakerScaleListener(WatchmakerScaleListener listener){
		watchmakerScaleSupport.removeWatchmakerScaleListener(listener);
	}
}
