package net.richarddawkins.watchmaker.scale;

import java.util.Vector;

public class WatchmakerScaleSupport {
	
	protected final Object source;
	
	public WatchmakerScaleSupport(Object source) {
		this.source = source;
	}
	
	protected Vector<WatchmakerScaleListener> listeners = new Vector<WatchmakerScaleListener>();
	
	public void addWatchmakerScaleListener(WatchmakerScaleListener listener) {
		listeners.add(listener);
	}
	public void removeWatchmakerScaleListener(WatchmakerScaleListener listener) {
		listeners.remove(listener);
	}

	public void fireWatchmakerScaleEvent(int scale) {
		fireWatchmakerScaleEvent(new WatchmakerScaleEvent(source, scale));
	}
	
	public void fireWatchmakerScaleEvent(WatchmakerScaleEvent event) {
		for(WatchmakerScaleListener listener: listeners) {
			listener.watchmakerScale(event);
		}
	}
}
