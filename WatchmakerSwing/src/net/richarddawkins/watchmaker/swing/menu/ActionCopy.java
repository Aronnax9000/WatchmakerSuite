package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.Morph;

public class ActionCopy extends SwingWatchmakerAction {

    private static final long serialVersionUID = 4121419685469500509L;

    public ActionCopy() {
        super("Copy", null, KeyStroke.getKeyStroke(KeyEvent.VK_C,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
        Morph morph = getAppData().getMorphOfTheHour();
        Image image = (Image) morph.getImage();
        MorphSelection imgSel = new MorphSelection(image, morph.getGenome());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgSel,
                null);
    }

    static DataFlavor genomeFlavor = new DataFlavor(Genome.class, "Genome");

    class MorphSelection implements Transferable {

        private Image image;
        private Genome genome;

        public MorphSelection(Image image, Genome genome) {
            this.image = image;
            this.genome = genome;
        }

        // Returns supported flavors
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { DataFlavor.imageFlavor,
                    ActionCopy.genomeFlavor };
        }

        // Returns true if flavor is supported
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor)
                    || ActionCopy.genomeFlavor.equals(flavor);
        }

        // Returns image
        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException, IOException {
            if (DataFlavor.imageFlavor.equals(flavor)) {
                return image;
            } else if (ActionCopy.genomeFlavor.equals(flavor)) {
                return genome;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }
}