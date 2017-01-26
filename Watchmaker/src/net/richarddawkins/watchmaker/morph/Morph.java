package net.richarddawkins.watchmaker.morph;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public interface Morph {
	
//	public void setMorphConfig(MorphConfig config);
//	public MorphConfig getMorphConfig();

	public void setGenome(Genome genome);
	public Genome getGenome();

	public void setPic(Phenotype pic);
	public Phenotype getPic();


    public MorphPedigree getPedigree();
    
    public Object getImage();
    public void setImage(Object object);
	void addPropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(PropertyChangeListener listener);
	public void firePropertyChange(PropertyChangeEvent event);
}