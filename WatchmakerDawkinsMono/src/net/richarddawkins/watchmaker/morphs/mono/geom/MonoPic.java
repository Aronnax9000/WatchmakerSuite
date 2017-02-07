package net.richarddawkins.watchmaker.morphs.mono.geom;

import net.richarddawkins.watchmaker.geom.Point;

public class MonoPic extends BiomorphPic {
    public void picLine(int x, int y, int xnew, int ynew, int thick) {
        if (thick > 8) {
            thick = 8;
        }
        if (lines.size() >= PICSIZEMAX) {
            // {Message(GetString(TooLargeString));}
            // {used the help dialog! v1.1 changed to alert}
            // DisplayError(-147, 'Biomorph too large, or other problem', '
            // ', StopError);
            // ExitToShell
        } else {
            Lin lin = new MonoLin(new Point(x,y), new Point(xnew,ynew), thick);
            
            addLin(lin);
        }
    }
}
