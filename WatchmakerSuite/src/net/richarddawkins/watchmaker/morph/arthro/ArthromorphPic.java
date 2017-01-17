package net.richarddawkins.watchmaker.morph.arthro;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Pic;
import net.richarddawkins.watchmaker.morph.biomorph.geom.QuickDrawColor;

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
