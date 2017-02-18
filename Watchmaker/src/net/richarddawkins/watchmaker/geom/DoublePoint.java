package net.richarddawkins.watchmaker.geom;

public class DoublePoint implements Cloneable {

	public double h;
	public double v;

	public DoublePoint() {
	}

	public DoublePoint(double h, double v) {
		this.h = h;
		this.v = v;
	}

	public DoublePoint add(DoublePoint p) {
		return new DoublePoint(this.h + p.h, this.v + p.v);
	}

	public DoublePoint subtract(DoublePoint p) {
		return new DoublePoint(this.h - p.h, this.v - p.v);
	}

	public DoublePoint clone() {
		return new DoublePoint(h, v);
	}

	public String toString() {
		return "(" + h + ", " + v + ")";
	}

	public Point toPoint(int scale) {
		return new Point((int) Math.round(scale * h), (int) Math.round(scale * v));
	}

}
