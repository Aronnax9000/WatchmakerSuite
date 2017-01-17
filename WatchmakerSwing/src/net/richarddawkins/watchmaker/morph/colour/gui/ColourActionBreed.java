package net.richarddawkins.watchmaker.morph.colour.gui;

import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.gui.ActionBreed;
import net.richarddawkins.watchmaker.gui.SwingAppData;
import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;

public class ColourActionBreed extends ActionBreed {

	private static final long serialVersionUID = 4121419685469500509L;
	public ColourActionBreed(SwingAppData swingAppData) {
		super(swingAppData, "Breed", new ImageIcon(
				ClassicImageLoader.getPicture("IconFlipBirdToBreedingGrid_ICON_00261_32x32").getImage()));
	}

}
