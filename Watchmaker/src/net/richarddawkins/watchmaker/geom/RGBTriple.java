package net.richarddawkins.watchmaker.geom;

public class RGBTriple {
    String name;
	public final int r;
	public final int g;
	public final int b;
    public RGBTriple(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public RGBTriple(int r, int g, int b, String name) {
        this(r,g,b);
        this.name = name;
    }
	@Override
	public String toString() {
	    return name + " #" + (Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b)).toUpperCase();
	}
}
