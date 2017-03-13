package net.richarddawkins.watchmaker.swing.drift;

import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.drift.DriftMorphViewPanel;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public class SwingDriftMorphViewPanel extends SwingMorphViewPanel
        implements DriftMorphViewPanel {

    public SwingDriftMorphViewPanel(MorphView morphView,
            BoxedMorphCollection page) {
        super(morphView, page);
       
    }

}
