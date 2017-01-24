package net.richarddawkins.watchmaker.morphs.arthro.phenotype;

import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morphs.bio.geom.Lin;
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
    public void picLine(int x, int y, int xnew, int ynew, int color, int thickness) {
        if (lines.size() >= PICSIZEMAX) {

            // {Message(GetString(TooLargeString));}
            // {used the help dialog! v1.1 changed to alert}
            // DisplayError(-147, 'Biomorph too large, or other problem', '
            // ', StopError);
            // ExitToShell
        } else {
            Lin movePtr = new ArthroLin(new Point(x, y), new Point(xnew, ynew), color, thickness);
            addLin(movePtr);
        }

    }

}
