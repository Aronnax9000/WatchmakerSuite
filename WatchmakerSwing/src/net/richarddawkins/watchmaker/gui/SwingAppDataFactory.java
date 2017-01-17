package net.richarddawkins.watchmaker.gui;

import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.MorphConfigFactory;
import net.richarddawkins.watchmaker.morph.MorphType;
import net.richarddawkins.watchmaker.morph.MorphTypeNotSupportedException;
import net.richarddawkins.watchmaker.morph.arthro.geom.gui.SwingArthroPicDrawer;
import net.richarddawkins.watchmaker.morph.arthro.gui.ArthromorphMenuBuilder;
import net.richarddawkins.watchmaker.morph.arthro.gui.ArthromorphSwingAppData;
import net.richarddawkins.watchmaker.morph.colour.geom.gui.SwingColourPicDrawer;
import net.richarddawkins.watchmaker.morph.colour.gui.ColourMenuBuilder;
import net.richarddawkins.watchmaker.morph.mono.geom.gui.SwingMonoPicDrawer;
import net.richarddawkins.watchmaker.morph.mono.gui.MonochromeMenuBuilder;
import net.richarddawkins.watchmaker.morph.snail.gui.SnailMenuBuilder;
import net.richarddawkins.watchmaker.morph.snail.gui.SnailSwingAppData;
import net.richarddawkins.watchmaker.morph.snail.gui.SwingSnailPicDrawer;

public class SwingAppDataFactory {

	public static SwingAppDataFactory newInstance(MorphType morphType) {
		return new SwingAppDataFactory(morphType);
	}

	private MorphType morphType;

	public SwingAppDataFactory(MorphType morphType) {
		this.morphType = morphType;
	}

	public SwingAppData newSwingAppData() {
		SwingAppData swingAppData;
		switch (morphType) {
		case ARTHROMORPH:
			swingAppData = new ArthromorphSwingAppData();
			break;
		case COLOUR_BIOMORPH:
			swingAppData = new ColourSwingAppData();
			break;
		case SNAIL:
			swingAppData = new SnailSwingAppData();
			break;
		case MONOCHROME_BIOMORPH:
			swingAppData = new MonochromeSwingAppData();
			break;
		default:
			swingAppData = new SimpleSwingAppData();

		}
		MorphConfigFactory factory = MorphConfigFactory.getInstance(morphType);
		if (factory != null) {
			try {
				MorphConfig config = factory.createConfig();
				config.setAppData(this);
				swingAppData.setMorphConfig(config);
				performTypeSpecificSetup(swingAppData);

			} catch (MorphTypeNotSupportedException e) {
				// Just go on to the next MorphType
			}
		}
		swingAppData.setMorphViewsTabbedPane(new MorphViewsTabbedPane(swingAppData));

		return swingAppData;
	}

	protected void performTypeSpecificSetup(SwingAppData swingAppData) {

		switch (morphType) {
		case ARTHROMORPH:
			swingAppData.setMenuBuilder(new ArthromorphMenuBuilder(swingAppData));
			swingAppData.setSwingPicDrawer(new SwingArthroPicDrawer());
			break;
		case COLOUR_BIOMORPH:
			swingAppData.setMenuBuilder(new ColourMenuBuilder(swingAppData));
			swingAppData.setSwingPicDrawer(new SwingColourPicDrawer());
			break;
		case SNAIL:
			swingAppData.setMenuBuilder(new SnailMenuBuilder(swingAppData));
			swingAppData.setSwingPicDrawer(new SwingSnailPicDrawer());
			break;
		case MONOCHROME_BIOMORPH:
			swingAppData.setMenuBuilder(new MonochromeMenuBuilder(swingAppData));
			swingAppData.setSwingPicDrawer(new SwingMonoPicDrawer());
			break;
		default:
		}

	}

}
