package net.richarddawkins.watchmaker.swing.breed;

import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewConfig;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewGridBoxManaged;

public class SwingBreedingMorphView extends SwingMorphViewGridBoxManaged {

    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.breed.SwingBreedingMorphView");
    private static final long serialVersionUID = -5445629768562940527L;

    protected BoxedMorph boxedMorphSpecial;

    Vector<Morph> litter;

    public SwingBreedingMorphView(SwingMorphViewConfig config) {
        super(config);

        SwingBreedingMorphViewPanel panel = new SwingBreedingMorphViewPanel(
                this, album.getPage(0));
        addPanel(panel);
        album.firstElement()
                .setBoxes(new GridBoxManager(appData.getDefaultBreedingCols(),
                        appData.getDefaultBreedingRows()));
        Morph morph = album.getFirstMorph();
        if (morph != null) {
            MorphConfig morphConfig = appData.getMorphConfig();
            Morph copy = morphConfig.newMorph();
            Genome genome = morphConfig.newGenome();
            morph.getGenome().copy(genome);
            copy.setGenome(genome);
            seedMorphs.add(copy);
        }
    }

    @Override
    public void seed() {
        synchronized (seedMorphs) {

            if (! seedMorphs.isEmpty()) {
                Morph morph = seedMorphs.firstElement();
                Morph parent;
                if (morph == null) {
                    MorphConfig config = appData.getMorphConfig();
                    parent = config
                            .newMorph(config.getStartingMorphBasicType());
                } else {
                    parent = morph;
                }
                BoxedMorphCollection boxedMorphCollection = album
                        .firstElement();
                BoxManager boxes = boxedMorphCollection.getBoxes();

                Rect midBox = boxes.getMidBox();
                BoxedMorph boxedMorph = new BoxedMorph(boxes, parent, midBox);
                boxedMorphCollection.removeAllElements();
                boxedMorphCollection.add(boxedMorph);

                // Trigger first breeding

                SwingBreedingMorphViewPanel panel = (SwingBreedingMorphViewPanel) panels
                        .firstElement();
                panel.setSpecial(midBox);
                if (appData.isBreedRightAway()) {
                    ((SwingBreedingMorphViewPanel) panels.firstElement())
                            .breedFromSpecial();
                }
                parent.setImage(null);

                seedMorphs.removeElementAt(0);
            }

            repaint();
        }
    }

}
