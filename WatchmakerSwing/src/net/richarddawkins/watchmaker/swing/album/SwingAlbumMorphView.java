package net.richarddawkins.watchmaker.swing.album;

import java.awt.GridLayout;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewConfig;

public class SwingAlbumMorphView extends SwingMorphView {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.album.SwingAlbumMorphView");

    
    
    @Override
    public void addPanels() {
        for (BoxedMorphCollection page : album.getPages()) {
            MorphViewPanel panel = new SwingAlbumMorphViewPanel(this, page);
            addPanel(panel);
        }
        
    }
    
    public SwingAlbumMorphView(SwingMorphViewConfig config) {
        super(config);
        this.setLayout(new GridLayout(2, 2));
    }

    @Override
    public Morph getMorphOfTheHour() {
        return album.getSelectedPage().getMorphOfTheHour();
    }

    private static final long serialVersionUID = 8224824610112892419L;

    @Override
    public void seed() {
        synchronized (seedMorphs) {
            Vector<Morph> seededMorphs = new Vector<Morph>();
            for (Morph seedMorph : seedMorphs) {
                findEmptyBox: for (BoxedMorphCollection page : this.album
                        .getPages()) {
                    BoxManager boxes = page.getBoxes();
                    for (Rect rect : boxes.getBoxes()) {
                        if (page.getBoxedMorph(rect) == null) {
                            BoxedMorph boxedMorph = new BoxedMorph(boxes,
                                    seedMorph, rect);
                            page.add(boxedMorph);
                            seededMorphs.add(seedMorph);
                            break findEmptyBox;
                        }
                    }
                }
            }
            seedMorphs.removeAll(seededMorphs);
            
        }

    }

    @Override
    public BoxManager newBoxManager() {
        return new GridBoxManager(5,3);
    }



}
