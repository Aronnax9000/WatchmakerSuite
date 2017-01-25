package net.richarddawkins.watchmaker.geom;

public class Dim {
	public Dim(int d, int e) {
		this.width = d;
		this.height = e;
	}
	public Dim(double width2, double height2) {
		this((int) width2, (int)height2);
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int width;
	public int height;
	@Override
	public String toString() {
		return "Dim:" + width + "x" + height;
	}
}
