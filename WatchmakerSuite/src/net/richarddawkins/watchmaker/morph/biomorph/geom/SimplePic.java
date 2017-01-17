package net.richarddawkins.watchmaker.morph.biomorph.geom;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphGenome;

public abstract class SimplePic extends Pic {
    
    
    public SimplePic(Morph morph) {
        this.morph = morph;
        calculatePicStyleType();
    }
    
    protected PicStyleType picStyle;
    
    public void calculatePicStyleType() {
        BiomorphGenome genome = (BiomorphGenome) morph.getGenome();
        picStyle = PicStyleType.FF;
        switch (genome.getCompletenessGene().getValue()) {
        case Single: {
            switch (genome.getSpokesGene().getValue()) {
            case NorthOnly:
                picStyle = PicStyleType.LF;
                break;
            case NSouth:
                picStyle = PicStyleType.LUD;
                break;
            case Radial:
                picStyle = PicStyleType.LUD;
                break;
            }
            break;
        }
        case Double:
            switch (genome.getSpokesGene().getValue()) {
            case NorthOnly: {
                picStyle = PicStyleType.FF;
                break;
            }
            case NSouth: {
                picStyle = PicStyleType.FUD;
                break;
            }
            case Radial: {
                picStyle = PicStyleType.FUD;
                break;
            }
            }
        }
    }

}
