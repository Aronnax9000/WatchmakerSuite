package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeSupport;

public abstract class SimpleGene implements Gene {
	public SimpleGene(String name) {
		this.name = name;
	}
	
	protected Genome genome;

	protected String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		String old = this.name;
//        this.vcs.fireVetoableChange( "name", old, name );
        this.name = name;
        this.pcs.firePropertyChange( "name", old, name );
	}

	@Override
	public Genome getGenome() {
		return genome;
	}

	@Override
	public void setGenome(Genome genome) {
		this.genome = genome;
	}

	protected PropertyChangeSupport pcs  = new PropertyChangeSupport(this);
	protected VetoableChangeSupport vcs  = new VetoableChangeSupport(this);
	@Override
	public void copy(Gene gene) {
		((SimpleGene) gene).name = name;
		
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
		
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
		
	}
	@Override
	public int getGooseSize() {
		
		return 1;
	}

	
}
