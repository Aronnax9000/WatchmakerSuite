package net.richarddawkins.watchmaker.swing.breed;

import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewConfig;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewGridBoxManaged;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;
import net.richarddawkins.watchmaker.util.Globals;

public class SwingBreedingMorphView extends SwingMorphViewGridBoxManaged {

    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphView");
    private static final long serialVersionUID = -5445629768562940527L;

    protected BoxedMorph boxedMorphSpecial;

    boolean freshlySeeded = false;
    Vector<Morph> litter;

    public SwingBreedingMorphView(SwingMorphViewConfig config) {
        super(config);
    }

    @Override

    public void addPanels() {
        SwingBreedingMorphViewPanel panel = new SwingBreedingMorphViewPanel(
                this, album.getPage(0));
        addPanel(panel);
    }

    @Override
    public BoxManager newBoxManager() {
        return new GridBoxManager(appData.getDefaultBreedingCols(),
                appData.getDefaultBreedingRows());
    }



}
