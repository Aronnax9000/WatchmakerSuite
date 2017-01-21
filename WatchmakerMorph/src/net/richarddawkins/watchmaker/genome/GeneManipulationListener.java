package net.richarddawkins.watchmaker.genome;

import java.util.EventListener;

public interface GeneManipulationListener extends EventListener {
	public void geneManipulated(GeneManipulationEvent gbme);
}
