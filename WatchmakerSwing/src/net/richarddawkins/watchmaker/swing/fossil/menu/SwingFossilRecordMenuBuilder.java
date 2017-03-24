package net.richarddawkins.watchmaker.swing.fossil.menu;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.image.ClassicImageLoader;
import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.menu.WatchmakerMenu;
import net.richarddawkins.watchmaker.menu.WatchmakerMenuBar;
import net.richarddawkins.watchmaker.morphview.fossil.FossilRecordMenuBuilder;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class SwingFossilRecordMenuBuilder extends FossilRecordMenuBuilder {

    public SwingFossilRecordMenuBuilder() {

    }
    @Override
    public void buildMenu(WatchmakerMenuBar menuBar) {
        WatchmakerMenu menu;
        menu = menuBar.getMenu("Operation");
        ClassicImageLoader loader = ClassicImageLoaderService.getInstance().getClassicImageLoader();
        AWTClassicImage classicImage = (AWTClassicImage) loader.getPicture("IconFossilRecord_ALAN_32x32");
        menu.add(new SwingWatchmakerAction("View Fossil Record", new ImageIcon(classicImage.getImage())) {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }});
    }
}
