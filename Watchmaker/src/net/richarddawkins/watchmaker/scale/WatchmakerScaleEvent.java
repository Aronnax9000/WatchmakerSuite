package net.richarddawkins.watchmaker.scale;

import java.util.EventObject;

public class WatchmakerScaleEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	protected final int scale;

	public WatchmakerScaleEvent(Object source, int scale) {
		super(source);
		this.scale = scale;
		
	}
	public int getScale() {
		return scale;
	}


}
