package net.richarddawkins.watchmaker.swing.drift;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.morphview.MorphViewConfig;
import net.richarddawkins.watchmaker.morphview.drift.DriftMorphView;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingDriftMorphView extends SwingMorphView
        implements DriftMorphView {


    public SwingDriftMorphView(MorphViewConfig config) {
        super(config);
        // TODO Auto-generated constructor stub
    }

    @Override
    public BoxManager newBoxManager() {
        // TODO Auto-generated method stub
        return null;
    }

}