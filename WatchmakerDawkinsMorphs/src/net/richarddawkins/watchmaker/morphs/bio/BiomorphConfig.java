package net.richarddawkins.watchmaker.morphs.bio;

import net.richarddawkins.watchmaker.morph.MorphType;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;

public abstract class BiomorphConfig extends SimpleMorphConfig {
	

  public BiomorphConfig(MorphType morphType) {
		super(morphType);
	}

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
