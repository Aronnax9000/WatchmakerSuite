package net.richarddawkins.watchmaker.morphs.colour.swing;

import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.menu.SwingActionBreed;

public class ColourActionBreed extends SwingActionBreed {

	private static final long serialVersionUID = 4121419685469500509L;
	public ColourActionBreed(AppData appData) {
		super(appData, "Breed", new ImageIcon(
				ClassicImageLoader.getPicture("IconFlipBirdToBreedingGrid_ICON_00261_32x32").getImage()));
	}

}
