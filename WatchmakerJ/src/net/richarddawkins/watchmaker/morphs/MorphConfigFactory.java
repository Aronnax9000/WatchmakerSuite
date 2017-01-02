package net.richarddawkins.watchmaker.morphs;

import net.richarddawkins.watchmaker.WatchmakerGUI;
import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphConfigImpl;
import net.richarddawkins.watchmaker.morphs.colour.ColourBiomorphConfigImpl;
import net.richarddawkins.watchmaker.morphs.mono.MonochromeBiomorphConfigImpl;
import net.richarddawkins.watchmaker.morphs.snail.SnailConfigImpl;

public class MorphConfigFactory {
	private MorphType morphType;
	
	protected MorphConfigFactory(MorphType morphType) {
		this.morphType = morphType;
	}
	
	public static MorphConfigFactory getInstance(MorphType morphType) {
		return new MorphConfigFactory(morphType);
	}
	public MorphConfig createConfig(WatchmakerGUI watchmakerGUI) {
		switch(morphType) {
		case MONOCHROME_BIOMORPH:
			return new MonochromeBiomorphConfigImpl(watchmakerGUI);
		case COLOUR_BIOMORPH:
			return new ColourBiomorphConfigImpl(watchmakerGUI);
		case SNAIL:
			return new SnailConfigImpl(watchmakerGUI);
		case ARTHROMORPH: 
			return new ArthromorphConfigImpl(watchmakerGUI);
		default:
			return null;
		}
	}
}
	
