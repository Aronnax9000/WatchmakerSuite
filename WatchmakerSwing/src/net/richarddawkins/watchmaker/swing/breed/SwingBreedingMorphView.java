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
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.phenotype.DrawingPreferences;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewConfig;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewGridBoxManaged;
import net.richarddawkins.watchmaker.util.Globals;

public class SwingBreedingMorphView extends SwingMorphViewGridBoxManaged {

    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphView");
    private static final long serialVersionUID = -5445629768562940527L;

    protected BoxedMorph boxedMorphSpecial;

    Vector<Morph> litter;
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
    @Override
    public void initBoxedMorphCollection(Album newAlbum, boolean engineeringMode) {
        super.initBoxedMorphCollection(newAlbum, engineeringMode);
        album.firstElement()
                .setBoxes(newBoxManager());
    }
    @Override
    public void addSeedMorphs(Vector<Morph> seedMorphs) {
        MorphConfig morphConfig = appData.getMorphConfig();
        this.addSeedMorph(
                morphConfig.newMorph(morphConfig.getStartingMorphBasicType()));
    }

    public SwingBreedingMorphView(SwingMorphViewConfig config) {
        super(config);
    }

    boolean freshlySeeded = false;

    @Override
    public void seed() {
        synchronized (seedMorphs) {

            if (!seedMorphs.isEmpty()) {
                logger.info("Seeding");

                Morph seedMorph = seedMorphs.firstElement();
                SwingBreedingMorphViewPanel panel = (SwingBreedingMorphViewPanel) panels
                        .firstElement();
                BoxedMorphCollection boxedMorphCollection = panel
                        .getBoxedMorphCollection();
                BoxManager boxes = boxedMorphCollection.getBoxes();
                
                boxedMorphCollection.clear();
                panel.setSelectedBox(null);
                Rect midBox = boxes.getMidBox();
                BoxedMorph boxedMorph = new BoxedMorph(boxes, seedMorph, midBox);
                boxedMorphCollection.removeAllElements();
                boxedMorphCollection.add(boxedMorph);
                logger.info("Added boxedMorph: " + boxedMorph);
                // Trigger first breeding

                panel.setSpecial(midBox);
                if (appData.isBreedRightAway()) {
                    ((SwingBreedingMorphViewPanel) panels.firstElement())
                            .breedFromSpecial();
                }
                seedMorph.setImage(null);

                Dim boxDim = boxes.getBox(0, panel.getDim()).getDim();
                Dim parentMorphDim = seedMorph.getPhenotype().getMargin().getDim();
                logger.info(" PanelDim:" + panel.getDim() + " BoxDim:" + boxDim
                        + " ParentMorphDim:" + parentMorphDim);
                int scale = boxDim.getScale(parentMorphDim, Globals.zoomBase);
                DrawingPreferences drawingPreferences = appData
                        .getPhenotypeDrawer().getDrawingPreferences();
                if (scale != boxes.getScale()) {
                    boxes.setScale(scale);
                }
                panel.setSelectedBox(midBox);
                seedMorphs.remove(seedMorph);
            }
            repaint();
        }
    }

}
