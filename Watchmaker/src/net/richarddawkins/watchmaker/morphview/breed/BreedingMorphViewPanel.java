package net.richarddawkins.watchmaker.morphview.breed;

import net.richarddawkins.watchmaker.morphview.MorphViewPanel;

public interface BreedingMorphViewPanel extends MorphViewPanel {

    void setBreedFromMidBoxOnNextRepaint(boolean b);

    void breedFromSelector();

    void breedFromSpecial();

}
