package net.richarddawkins.watchmaker.swing.driftsweep;

import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.drift.DriftMorphViewPanel;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public class SwingDriftSweepMorphViewPanel extends SwingMorphViewPanel
        implements DriftMorphViewPanel {

    public SwingDriftSweepMorphViewPanel(MorphView morphView,
            BoxedMorphCollection page) {
        super(morphView, page);
       
    }

}
