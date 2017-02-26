package net.richarddawkins.watchmaker.swing.menu;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;

public class ActionNewRandomStart extends SwingWatchmakerAction {

    private static final long serialVersionUID = 1L;

    public ActionNewRandomStart(AppData appData) {
        super(appData, "Hopeful Monster (New Random Start)",
                new ImageIcon(ClassicImageLoader
                        .getPicture("SixSidedDieShowsFiveIcon_ICON_00257_32x32")
                        .getImage()),
                KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.ALT_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MorphConfig config = appData.getMorphConfig();
        Morph morph = appData.getMorphOfTheHour();
        Genome genome = config.getGenomeFactory().deliverSaltation();
        morph.setGenome(genome);
        MorphView morphView = appData.getMorphViewsTabbedPane()
                .getSelectedMorphView();
        morphView.addSeedMorph(morph);
    }
}
