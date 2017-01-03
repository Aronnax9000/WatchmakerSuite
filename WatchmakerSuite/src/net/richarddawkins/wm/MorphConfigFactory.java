package net.richarddawkins.wm;

import net.richarddawkins.watchmaker.morph.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morph.arthro.ArthromorphMutagen;
import net.richarddawkins.watchmaker.morph.colour.ColourBiomorphConfig;
import net.richarddawkins.watchmaker.morph.colour.ColourMutagen;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.common.MorphType;
import net.richarddawkins.watchmaker.morph.common.Mutagen;
import net.richarddawkins.watchmaker.morph.mono.MonochromeMorphConfig;
import net.richarddawkins.watchmaker.morph.mono.MonochromeMutagen;
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
		  config = new MonochromeMorphConfig(watchmakerGUI);
		  config.setMutagen((Mutagen)new MonochromeMutagen(config));
			break;
		case COLOUR_BIOMORPH:
			config = new ColourBiomorphConfig(watchmakerGUI);
			config.setMutagen((Mutagen)new ColourMutagen((ColourBiomorphConfig)config));
			break;
		case SNAIL:
			config = new SnailConfig(watchmakerGUI);
			config.setMutagen((Mutagen)new SnailMutagen((SnailConfig)config));
			break;
		case ARTHROMORPH: 
			config = new ArthromorphConfig(watchmakerGUI);
			config.setMutagen((Mutagen)new ArthromorphMutagen(config));
			break;
		default:
		  break;
		}
		return config;
	}
}
	
