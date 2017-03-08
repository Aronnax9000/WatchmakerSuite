package net.richarddawkins.watchmaker.swing.drift;

import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.drift.DriftMorphViewPanel;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public class SwingDriftMorphViewPanel extends SwingMorphViewPanel
        implements DriftMorphViewPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SwingDriftMorphViewPanel(MorphView morphView,
            BoxedMorphCollection page) {
        super(morphView, page);
        // TODO Auto-generated constructor stub
    }

}
