package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeEvent;
import java.util.EventObject;

public class GenomeChangeEvent extends EventObject {


	private static final long serialVersionUID = 1L;

	protected final Genome genome;
	protected final PropertyChangeEvent propertyChangeEvent;
	
	public PropertyChangeEvent getPropertyChangeEvent() {
		return propertyChangeEvent;
	}


	public Genome getGenome() {
		return genome;
	}


	public GenomeChangeEvent(Object source, Genome genome, PropertyChangeEvent event) {
		super(source);
		this.propertyChangeEvent = event;
		this.genome = genome;
	}

}
