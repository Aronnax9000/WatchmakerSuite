package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeEvent;
import java.util.Vector;

public class GenomeChangeSupport {
	
	protected final Object source;
	
	public GenomeChangeSupport(Object source) {
		this.source = source;
	}
	protected Vector<GenomeChangeListener> listeners = new Vector<GenomeChangeListener>();
	
	public void addGenomeChangeListener(GenomeChangeListener listener) {
		listeners.add(listener);
	}
	public void removeGenomeChangeListener(GenomeChangeListener listener) {
		listeners.remove(listener);
	}
	
	public void fireGenomeChangeEvent(Genome genome, PropertyChangeEvent pce) {
		GenomeChangeEvent evt = new GenomeChangeEvent(source, genome, pce);
		for(GenomeChangeListener listener: listeners) {
			listener.genomeChange(evt);
		}
	}
	

}
