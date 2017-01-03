package net.richarddawkins.wm.morphs.biomorph;

import net.richarddawkins.wm.morphs.SimpleMorphConfig;

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
