package net.richarddawkins.watchmaker.swing.morphview;

import java.util.Iterator;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;

public abstract class SwingMorphViewGridBoxManaged extends SwingMorphView {
    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewGridBoxManaged");

    private static final long serialVersionUID = 1L;


    public SwingMorphViewGridBoxManaged(SwingMorphViewConfig config) {
        super(config);
    }

    @Override
    public void undo() {
        BoxedMorphCollection boxedMorphCollection = getSelectedPanel().getBoxedMorphCollection();
        int level = album.indexOfPage(boxedMorphCollection);
        if (level > 0) {
            logger.info("Undo level: " + level + " size " + album.size());
            BoxedMorphCollection oldValue = boxedMorphCollection;
            boxedMorphCollection = album.getPage(level - 1);
            repaint();
        } else {
            // In Engineering, current boxedMorphCollection isn't part of
            // album.
            logger.info("Undo Engineering Mode album size " + album.size());
            backup(true);
            BoxedMorphCollection oldValue = boxedMorphCollection;
            boxedMorphCollection = album.getPreviousPage(album.getLastPage());
            repaint();
        }
    }

    @Override
    public void redo() {
        BoxedMorphCollection boxedMorphCollection = album.firstElement();

        int level = album.indexOfPage(boxedMorphCollection);
        if (level < album.size() - 1) {
            logger.info("Redo level: " + level + " size " + album.size());
            BoxedMorphCollection oldValue = boxedMorphCollection;
            boxedMorphCollection = album.getPage(level + 1);
            repaint();
        }
    }

    @Override
    public void backup(boolean copyMorph) {

        logger.fine("Backup " + copyMorph + " started " + album.size());
        BoxedMorphCollection boxedMorphCollection = this.getSelectedPanel().getBoxedMorphCollection();
        GridBoxManager oldBoxManager = (GridBoxManager) boxedMorphCollection
                .getBoxes();
        GridBoxManager backupBoxManager = new GridBoxManager(oldBoxManager.cols,
                oldBoxManager.rows);
        BoxedMorphCollection backupBoxedMorphs = new BoxedMorphCollection(
                "backing", backupBoxManager);
        Iterator<Rect> boxIterator = backupBoxManager.getBoxes().iterator();
        for (BoxedMorph boxedMorph : boxedMorphCollection.getBoxedMorphs()) {
            Rect box;
            if (boxedMorphCollection.size() == 1) {
                box = backupBoxManager.getMidBox();
            } else {
                box = boxIterator.next();
            }

            Morph newMorph;
            if (copyMorph) {
                newMorph = appData.getMorphConfig()
                        .copyMorph(boxedMorph.getMorph());
                
            } else {
                newMorph = boxedMorph.getMorph();
            }
            BoxedMorph newBoxedMorph = new BoxedMorph(backupBoxManager,
                    newMorph, box);

            backupBoxedMorphs.add(newBoxedMorph);
        }
        // Add the backup just behind the one on the end (so we still work on
        // the original)
        album.addPage(album.size() - 1, backupBoxedMorphs);
        if (album.size() > Album.MAX_PAGES) {
            album.removePage(0);
        }
        logger.fine("Backup " + copyMorph + " finished " + album.size());
    }


}
