package net.richarddawkins.watchmaker.morph;

import net.richarddawkins.watchmaker.morph.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morph.arthro.genome.ArthromorphMutagen;
import net.richarddawkins.watchmaker.morph.colour.ColourBiomorphConfig;
import net.richarddawkins.watchmaker.morph.colour.genome.ColourMutagen;
import net.richarddawkins.watchmaker.morph.mono.MonochromeMorphConfig;
import net.richarddawkins.watchmaker.morph.mono.genome.MonochromeMutagen;
import net.richarddawkins.watchmaker.morph.snail.SnailConfig;
import net.richarddawkins.watchmaker.morph.snail.SnailMutagen;

public class MorphConfigFactory {
	private MorphType morphType;
	
	protected MorphConfigFactory(MorphType morphType) {
		this.morphType = morphType;
	}
	public static MorphConfigFactory getInstance(MorphType morphType) {
		MorphConfigFactory factory = new MorphConfigFactory(morphType);
		return factory;
	}
	public MorphConfig createConfig() throws MorphTypeNotSupportedException {
		MorphConfig config = null;
		switch(morphType) {
		case MONOCHROME_BIOMORPH:
		  config = new MonochromeMorphConfig();
		  config.setMutagen((Mutagen)new MonochromeMutagen(config));
			break;
		case COLOUR_BIOMORPH:
			config = new ColourBiomorphConfig();
			config.setMutagen((Mutagen)new ColourMutagen((ColourBiomorphConfig)config));
			break;
		case SNAIL:
			config = new SnailConfig();
			config.setMutagen((Mutagen)new SnailMutagen((SnailConfig)config));
			break;
		case ARTHROMORPH: 
			config = new ArthromorphConfig();
			config.setMutagen((Mutagen)new ArthromorphMutagen(config));
			break;
		default:
			throw new MorphTypeNotSupportedException();
			
		}			
			
		return config;
	}
}
	
