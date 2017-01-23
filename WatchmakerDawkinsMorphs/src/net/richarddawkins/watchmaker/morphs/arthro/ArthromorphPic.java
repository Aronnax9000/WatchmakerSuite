package net.richarddawkins.watchmaker.morphs.arthro;

import net.richarddawkins.watchmaker.morphs.bio.geom.QuickDrawColor;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourPic;

public class ArthromorphPic extends ColourPic  {
    protected Integer limbFillColor = QuickDrawColor.GREEN;

    public Integer getLimbFillColor() {
        return limbFillColor;
    }

    public void setLimbFillColor(Integer limbFillColor) {
        this.limbFillColor = limbFillColor;
    }


}
