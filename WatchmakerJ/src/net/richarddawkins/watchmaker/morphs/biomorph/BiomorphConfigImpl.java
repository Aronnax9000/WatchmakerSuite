package net.richarddawkins.watchmaker.morphs.biomorph;

import net.richarddawkins.watchmaker.morphs.SimpleMorphConfig;

public abstract class BiomorphConfigImpl extends SimpleMorphConfig {
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
