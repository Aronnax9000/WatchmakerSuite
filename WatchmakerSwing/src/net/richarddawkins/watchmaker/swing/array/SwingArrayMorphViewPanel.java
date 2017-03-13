package net.richarddawkins.watchmaker.swing.array;

import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.array.ArrayMorphView;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public abstract class SwingArrayMorphViewPanel extends SwingMorphViewPanel
        implements ArrayMorphView {



    public SwingArrayMorphViewPanel(MorphView morphView,
            BoxedMorphCollection page) {
        super(morphView, page);
        // TODO Auto-generated constructor stub
    }

}
