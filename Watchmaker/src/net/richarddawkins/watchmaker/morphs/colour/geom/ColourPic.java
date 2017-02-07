package net.richarddawkins.watchmaker.morphs.colour.geom;



import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbShapeType;
import net.richarddawkins.watchmaker.morphs.mono.geom.BiomorphPic;
import net.richarddawkins.watchmaker.morphs.mono.geom.Lin;

public class ColourPic extends BiomorphPic {

    public static final int RED = 0;
	public static final int GREEN = 0;
	public static final int LIGHT_GRAY = 0;
	protected int backgroundColor;
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    protected int thickness;
    protected LimbFillType limbFill;
    protected LimbShapeType limbShape;
    

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public LimbFillType getLimbFill() {
        return limbFill;
    }

    public void setLimbFill(LimbFillType limbFill) {
        this.limbFill = limbFill;
    }

    public LimbShapeType getLimbShape() {
        return limbShape;
    }

    public void setLimbShape(LimbShapeType limbShape) {
        this.limbShape = limbShape;
    }



    int[] colorVals = { 0, 51, 102, 153, 204, 255 };
    int[] backColorVals = { 255, 204, 153, 102, 51, 0 };

    public void picLine(int x, int y, int xnew, int ynew, int thick, int color) {
        if (lines.size() >= PICSIZEMAX) {

            // {Message(GetString(TooLargeString));}
            // {used the help dialog! v1.1 changed to alert}
            // DisplayError(-147, 'Biomorph too large, or other problem', '
            // ', StopError);
            // ExitToShell
        } else {
            Lin movePtr = new ColourLin(new Point(x, y), new Point(xnew, ynew), thick, color);
            addLin(movePtr);
        }
    }

}
