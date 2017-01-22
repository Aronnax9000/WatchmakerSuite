package net.richarddawkins.watchmaker.morphs.bio.embryo;

import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphs.bio.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morphs.bio.geom.BiomorphPic;
import net.richarddawkins.watchmaker.morphs.bio.geom.BiomorphPic.PicStyleType;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class BiomorphEmbryology implements Embryology {
	
	protected int plugIn(int[] gene, int[] dx, int[] dy) {
		int order = gene[8];
		dx[3] = gene[0];
		dx[4] = gene[1];
		dx[5] = gene[2];
		dy[2] = gene[3];
		dy[3] = gene[4];
		dy[4] = gene[5];
		dy[5] = gene[6];
		dy[6] = gene[7];
		dx[1] = -dx[3];
		dy[1] = dy[3];
		dx[0] = -dx[4];
		dy[0] = dy[4];
		dx[7] = -dx[5];
		dy[7] = dy[5];
		dx[2] = 0;
		dx[6] = 0;
		return order;
	}
	

	@Override
	public void develop(Genome biomorphGenome, Phenotype biomorphPic) {
	        BiomorphGenome genome = (BiomorphGenome) biomorphGenome;
	        BiomorphPic pic = (BiomorphPic) biomorphPic;
	        
	        switch (genome.getCompletenessGene().getValue()) {
	        case Single: {
	            switch (genome.getSpokesGene().getValue()) {
	            case NorthOnly:
	            	pic.picStyle = PicStyleType.LF;
	                break;
	            case NSouth:
	            	pic.picStyle = PicStyleType.LUD;
	                break;
	            case Radial:
	            	pic.picStyle = PicStyleType.LUD;
	                break;
	            }
	            break;
	        }
	        case Double:
	            switch (genome.getSpokesGene().getValue()) {
	            case NorthOnly: {
	            	pic.picStyle = PicStyleType.FF;
	                break;
	            }
	            case NSouth: {
	            	pic.picStyle = PicStyleType.FUD;
	                break;
	            }
	            case Radial: {
	            	pic.picStyle = PicStyleType.FUD;
	                break;
	            }
	         
	        }
	    }
	}

}
