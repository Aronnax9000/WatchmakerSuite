package net.richarddawkins.watchmaker.genome;

import java.util.Vector;

public class GeneManipulationAdapter implements GeneManipulationSupport {

	protected Vector<GeneManipulationListener> listeners = new Vector<GeneManipulationListener>();
	
	@Override
	public void fireGeneManipulationEvent(GeneManipulationEvent event) {
		for(GeneManipulationListener listener: listeners) {
			listener.geneManipulated(event);
		}
		
	}

	@Override
	public void addGeneManipulationListener(GeneManipulationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeGeneManipulationListener(GeneManipulationListener listener) {
		listeners.remove(listener);
	}

	@Override
	public GeneManipulationListener[] getGeneManipulationListeners() {
		return (GeneManipulationListener[]) listeners.toArray();
	}

	@Override
	public void removeAllGeneManipulationListeners() {
		listeners.clear();
	}
}
