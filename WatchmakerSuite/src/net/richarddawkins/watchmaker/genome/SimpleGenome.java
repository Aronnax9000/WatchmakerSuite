package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import net.richarddawkins.watchmaker.morph.Morph;

public abstract class SimpleGenome implements Genome {

  protected Morph morph;

  protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
  
  @Override
  public Morph getMorph() {
    return morph;
  }

  @Override
  public void setMorph(Morph morph) {
    this.morph = morph;
  }

public Gene[] toGeneArray() {
	return null;
}
public Gene getGene(int i) {
	return toGeneArray()[i];
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
public void propertyChange(PropertyChangeEvent evt) {
	pcs.firePropertyChange(evt);
}

}
