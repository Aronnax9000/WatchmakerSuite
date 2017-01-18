package net.richarddawkins.watchmaker.morphs.colour.swing;

import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.menubuilder.ActionBreed;

public class ColourActionBreed extends ActionBreed {

	private static final long serialVersionUID = 4121419685469500509L;
	public ColourActionBreed(SwingAppData swingAppData) {
		super(swingAppData, "Breed", new ImageIcon(
				ClassicImageLoader.getPicture("IconFlipBirdToBreedingGrid_ICON_00261_32x32").getImage()));
	}

}
