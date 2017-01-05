package net.richarddawkins.watchmaker.morph.common;

public abstract class BiomorphConfig extends SimpleMorphConfig {
	

  protected boolean[] mut;

  public void setMut(int i, boolean newValue) {
    boolean oldValue = mut[i];
    mut[i] = newValue;
    if (oldValue != newValue) {
      pcs.firePropertyChange("mut[" + i + "]", oldValue, newValue);
    }
  }

  public boolean getMut(int i) {
    return mut[i];
  }

  public boolean[] getMut() {
    return mut;
  }

}
