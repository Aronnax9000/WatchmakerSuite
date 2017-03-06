package net.richarddawkins.watchmaker.swing.breed.menu;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class SwingActionBreed extends SwingWatchmakerAction  {

	private static final long serialVersionUID = 4121419685469500509L;

	public SwingActionBreed(AppData appData) {
		super(appData, "Breed", new ImageIcon(
				ClassicImageLoader.getPicture("IconFlipBirdToBreedingGrid_ICON_00261_32x32").getImage()));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Morph morph = appData.getMorphOfTheHour();
		appData.addBreedingMorphView(morph);
	}

}
