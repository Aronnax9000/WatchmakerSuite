package net.richarddawkins.watchmaker.swing.drift;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.morphview.drift.DriftMorphView;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewConfig;

public class SwingDriftMorphView extends SwingMorphView
        implements DriftMorphView {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SwingDriftMorphView(SwingMorphViewConfig config) {
        super(config);
        // TODO Auto-generated constructor stub
    }

    @Override
    public BoxManager newBoxManager() {
        // TODO Auto-generated method stub
        return null;
    }

}
