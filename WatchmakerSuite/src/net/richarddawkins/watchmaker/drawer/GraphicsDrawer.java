package net.richarddawkins.watchmaker.drawer;

import java.awt.Graphics2D;
import java.awt.Point;

public interface GraphicsDrawer {

	void setPosition(Point position);
	void draw(Graphics2D g);
	void setDestination(Point destination);
	Point getDestination();
	void setProgress(double progress);
	double getProgress();

}
