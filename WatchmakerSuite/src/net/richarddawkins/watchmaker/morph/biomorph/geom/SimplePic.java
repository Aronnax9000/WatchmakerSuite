package net.richarddawkins.watchmaker.morph.biomorph.geom;

import java.awt.Color;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphGenome;

public abstract class SimplePic extends Pic {
    
    protected final Morph morph;
    
    public SimplePic(Morph morph) {
        this.morph = morph;
    }
    
    

    public final void picLine(int xx1, int yy1, int xx2, int yy2) {
        picLine(xx1, yy1, xx2, yy2, 1, Color.BLACK);
    }

    public final void picLine(int xx1, int yy1, int xx2, int yy2, int thick) {
        picLine(xx1, yy1, xx2, yy2, thick, Color.BLACK);
    }

    public final void picLine(int xx1, int yy1, int xx2, int yy2, Color color) {
        picLine(xx1, yy1, xx2, yy2, 1, color);
    }
    
    public PicStyleType getPicStyleType() {
        BiomorphGenome genome = (BiomorphGenome) morph.getGenome();
        PicStyleType picStyle = PicStyleType.FF;
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
        return picStyle;
    }

}
