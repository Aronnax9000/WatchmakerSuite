package net.richarddawkins.wm.morphs;

import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.common.Mutagen;
import net.richarddawkins.wm.WatchmakerGUI;
import net.richarddawkins.wm.morphs.arthro.ArthromorphConfig;
import net.richarddawkins.wm.morphs.arthro.ArthromorphMutagen;
import net.richarddawkins.wm.morphs.colour.ColourBiomorphConfig;
import net.richarddawkins.wm.morphs.colour.ColourMutagen;
import net.richarddawkins.wm.morphs.mono.MonochromeBiomorphConfig;
import net.richarddawkins.wm.morphs.mono.MonochromeMutagen;
import net.richarddawkins.wm.morphs.snail.SnailConfig;
import net.richarddawkins.wm.morphs.snail.SnailMutagen;

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
		  config.setMutagen((Mutagen)new MonochromeMutagen(config));
			break;
		case COLOUR_BIOMORPH:
			config = new ColourBiomorphConfig(watchmakerGUI);
			config.setMutagen((Mutagen)new ColourMutagen(config));
			break;
		case SNAIL:
			config = new SnailConfig(watchmakerGUI);
			config.setMutagen((Mutagen)new SnailMutagen(config));
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
	
