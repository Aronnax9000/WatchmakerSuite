package net.richarddawkins.watchmaker.morphs;

import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.MorphTypeNotSupportedException;
import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.genome.ArthromorphMutagen;
import net.richarddawkins.watchmaker.morphs.colour.ColourBiomorphConfig;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColourMutagen;
import net.richarddawkins.watchmaker.morphs.concho.SnailConfig;
import net.richarddawkins.watchmaker.morphs.concho.SnailMutagen;
import net.richarddawkins.watchmaker.morphs.mono.MonochromeMorphConfig;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeMutagen;

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
	
