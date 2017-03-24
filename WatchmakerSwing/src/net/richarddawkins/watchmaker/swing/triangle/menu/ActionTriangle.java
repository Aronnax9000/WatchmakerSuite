package net.richarddawkins.watchmaker.swing.triangle.menu;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ActionTriangle extends SwingWatchmakerAction {


	private static final long serialVersionUID = 4121419685469500509L;
	

	public ActionTriangle() {
		super("Triangle",
                new ImageIcon(((AWTClassicImage) ClassicImageLoaderService
                        .getInstance().getClassicImageLoader()
                        .getPicture("IconTriangle_ALAN_32x32")).getImage()));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    getAppData().addTriangleMorphView();
	}

}
