package net.richarddawkins.watchmaker.pedigree;

import net.richarddawkins.watchmaker.morphview.MorphView;

public interface PedigreeMorphView extends MorphView {
    void setMirrorType(MirrorType mirrorType);
    MirrorType getMirrorType();
}
