package net.richarddawkins.watchmaker.morphs;

import net.richarddawkins.watchmaker.WatchmakerGUI;
import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphMutagen;
import net.richarddawkins.watchmaker.morphs.colour.ColourBiomorphConfig;
import net.richarddawkins.watchmaker.morphs.colour.ColourMutagen;
import net.richarddawkins.watchmaker.morphs.mono.MonochromeBiomorphConfig;
import net.richarddawkins.watchmaker.morphs.mono.MonochromeMutagen;
import net.richarddawkins.watchmaker.morphs.snail.SnailConfig;
import net.richarddawkins.watchmaker.morphs.snail.SnailMutagen;

public class MorphConfigFactory {
	private MorphType morphType;
	
	protected MorphConfigFactory(MorphType morphType) {
		this.morphType = morphType;
	}
	
	public static MorphConfigFactory getInstance(MorphType morphType) {
		return new MorphConfigFactory(morphType);
	}
	public MorphConfig createConfig(WatchmakerGUI watchmakerGUI) {
	  MorphConfig config = null;
		switch(morphType) {
		case MONOCHROME_BIOMORPH:
		  config = new MonochromeBiomorphConfig(watchmakerGUI);
		  config.setMutagen(new MonochromeMutagen(config));
			break;
		case COLOUR_BIOMORPH:
			config = new ColourBiomorphConfig(watchmakerGUI);
      config.setMutagen(new ColourMutagen(config));
			break;
		case SNAIL:
			config = new SnailConfig(watchmakerGUI);
      config.setMutagen(new SnailMutagen(config));
			break;
		case ARTHROMORPH: 
			config = new ArthromorphConfig(watchmakerGUI);
      config.setMutagen(new ArthromorphMutagen(config));
			break;
		default:
		  break;
		}
		return config;
	}
}
	
