package net.richarddawkins.watchmaker.swing.fossil;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewConfig;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewGridBoxManaged;

public class SwingFossilMorphView extends SwingMorphViewGridBoxManaged implements MorphView {


    private static final long serialVersionUID = 1L;

    public SwingFossilMorphView(SwingMorphViewConfig config) {
        super(config);
        // TODO Auto-generated constructor stub
    }
    @Override
    public BoxManager newBoxManager() {
        return new GridBoxManager(1,1);
    }

    @Override
    public void seed() {
        // TODO Auto-generated method stub
        
    }

 }
