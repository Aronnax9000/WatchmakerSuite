package net.richarddawkins.watchmaker.genome;



public interface GeneManipulationSupport {

	public void fireGeneManipulationEvent(GeneManipulationEvent event);
	
	public void addGeneManipulationListener(GeneManipulationListener listener);
	public void removeGeneManipulationListener(GeneManipulationListener listener);
	public GeneManipulationListener[] getGeneManipulationListeners();

	void removeAllGeneManipulationListeners();
	
}
