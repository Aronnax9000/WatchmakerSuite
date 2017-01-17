package net.richarddawkins.watchmaker.morphs.arthro;

import net.richarddawkins.watchmaker.geom.Pic;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.bio.geom.QuickDrawColor;

public class ArthromorphPic extends Pic  {
    protected Integer limbFillColor = QuickDrawColor.GREEN;

    public Integer getLimbFillColor() {
        return limbFillColor;
    }

    public void setLimbFillColor(Integer limbFillColor) {
        this.limbFillColor = limbFillColor;
    }

    public ArthromorphPic(Morph morph) {
        super(morph);
    }
}
