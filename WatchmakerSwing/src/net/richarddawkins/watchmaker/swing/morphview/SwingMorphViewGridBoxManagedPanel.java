package net.richarddawkins.watchmaker.swing.morphview;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;

public class SwingMorphViewGridBoxManagedPanel extends SwingMorphViewPanel {

    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewGridBoxManagedPanel");


    public SwingMorphViewGridBoxManagedPanel(MorphView morphView, BoxedMorphCollection page) {
        super(morphView, page);
    }
}
