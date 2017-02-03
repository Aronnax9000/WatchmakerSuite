package net.richarddawkins.watchmaker.morphs.concho.geom;

import net.richarddawkins.watchmaker.geom.Point;

/*(*
 * {@code 
 * type RealPoint = RECORD
 *  x, y: real;
 * end;    
 * }
 */
public class RealPoint {
	
	public RealPoint() {}
	
	public RealPoint(double x,double y) {
		this.x = x;
		this.y = y;
	}
	public double x = 0;
	public double y = 0;
	
	public RealPoint copy() {
		return new RealPoint(x,y);
	}
	
	public RealPoint scale(double scale) {
		return new RealPoint(x * scale, y * scale);
	}
	
	public Point toPoint() {
		return new Point((int)Math.round(x), (int)Math.round(y));
	}
	
}
