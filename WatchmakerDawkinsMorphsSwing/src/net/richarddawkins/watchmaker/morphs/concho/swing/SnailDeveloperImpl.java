package net.richarddawkins.watchmaker.morphs.concho.swing;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.richarddawkins.watchmaker.geom.Lin;
import net.richarddawkins.watchmaker.geom.Pic;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailGenome;
import net.richarddawkins.watchmaker.morphs.concho.genome.type.HandednessType;
import net.richarddawkins.watchmaker.swing.images.ClassicImage;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.util.ModeType;

public class SnailDeveloperImpl {
	interface DevelopDrawers {
		public void drawShell();

		public void drawTop();
	}
	interface GoSub8000 {
		public void goSub8000();
	}
	public static boolean clipBoarding;
	public static boolean delayedDrawing;
	public static boolean dontDraw;
	// public Pic myPic = new SnailPic(this);
	public static int inc;
	/**
	 * Largest Generating Curve ID, homologous with the resource ids of the
	 * original Macintosh resource file ids.
	 */
	public final static int MaxResources = 154;
	public static boolean sideView;

	public static ModeType theMode;

	public static boolean zeroMargin;

	int currentGeneratingCurve = 0; // FIXME

	public final Rect margin = new Rect();

	/**
	 * Originally (in Pascal) a nested procedure within drawPic.
	 * 
	 * @param g2
	 *            graphics context to draw on
	 * @param thisPic
	 *            the pic to draw
	 * @param lin
	 * @param theScale
	 * @param place
	 * @param threshold
	 * @param biomorph
	 * @param jLinIndex
	 * @param jThreshold
	 */
	public void actualLine(Graphics2D g2, Pic thisPic, Lin lin, double theScale, Point place, int threshold,
			SnailGenome biomorph, int jLinIndex, int jThreshold) {
		boolean doMirror, tooThin;
		quarantine(thisPic, lin, theScale);
		int vertOffset = (int) Math.round(theScale * (-place.v));
		int horizOffset = (int) Math.round(theScale * (-place.h));
		int y0 = lin.startPt.v - vertOffset;
		int y1 = lin.endPt.v - vertOffset;
		int x0 = lin.startPt.h - horizOffset;
		int x1 = lin.endPt.h - horizOffset;

		if (!sideView) {
			g2.drawLine(x0, y0, x1, y1);
		} else {
			doMirror = false;
			if (x1 < x0) { // here we need a mirror image
				doMirror = true;
				int temp = x0;
				x0 = x1;
				x1 = temp;
			}
			;
			Rect theRect = new Rect(x0, y0, x1, y1);
			int width = Math.abs(theRect.right - theRect.left);
			int height = Math.abs(theRect.bottom - theRect.top);
			tooThin = width < threshold || height < threshold;
			if (biomorph.generatingCurve.getValue() == 0 || theMode == ModeType.Triangling
					|| (jLinIndex < jThreshold && tooThin)) {
				if (width == 0) {
					g2.drawLine(theRect.left, theRect.top, theRect.right, theRect.bottom);
				} else {
					g2.drawOval(theRect.left, theRect.top, theRect.getWidth(), theRect.getHeight());
				}
			} else {
				if (width % 2 == 1)
					theRect.right++;
				if (!doMirror) {
					// CopyBits(MugShot, SaveBitMap, RectOfInterest, theRect,
					// srcOr, NIL)
				} else {
					// CopyBits(Mirrorshot, SaveBitMap, RectOfInterest, theRect,
					// srcOr, NIL);
				}
			}
		}
	}

	void changeTheBitMaps(SnailGenome biomorph) {

		ClassicImage strangePicture = ClassicImageLoader.getGeneratingCurve(biomorph.generatingCurve.getValue());
		Rect rectOfInterest = new Rect();
		BufferedImage strangePictureImage = strangePicture.getImage();
		rectOfInterest.setRect(0, 0, strangePictureImage.getWidth(), strangePictureImage.getHeight());
		if ((rectOfInterest.getWidth() & 1) == 1) // odd width
			rectOfInterest.right++;
		// ClipRect(rectOfInterest);
		// SetPortBits(MugShot);
		// FillRect(RectOfInterest, White); {Off screen}
		// DrawPicture(StrangePicture, RectOfInterest);
		// Cliprect(Prect);
		// SetPortBits(SaveBitMap);
		// MirrorBits(MugShot, RectOfInterest, MirrorShot);
	}

	Point develop(Graphics2D graphics, final SnailGenome theShell, final Point where, Rect box) {
		final Point centre = new Point();
		int offSet, height, width, newheight, newwidth, newtop, newbottom, newleft, newright;

		DevelopDrawers drawers = new DevelopDrawers() {
			double denom, theSize, w, d;
			double i, bd, wi;
			int m, start;
			double mnx, mny;
			double rad;
			double x1, y1;
			double x2, y2;
			double xu1, yu1, xu2, yu2;
			double xv1, yv1;
			double xv2, yv2;

			public void drawShell() {

				double rad1, rad2, rad, twoPi, i, fw, w, d, s, t, p;
				int m, inc;
				double grunge, f, g, h, k, xc, yc, xr, yr, mnx, mny;
				Rect theRect = new Rect();
				double denom, theSize;
				// SetUp
				theSize = 0.8;
				denom = 136 * theSize;
				w = theShell.opening.getValue();
				d = theShell.displacement.getValue();
				s = theShell.shape.getValue();
				t = theShell.translation.getValue();
				rad = 100;
				twoPi = 2 * Math.PI;
				mny = Math.round(-(100 / denom) * where.v * 1.088);
				mnx = Math.round(-(100 / denom) * where.h * 1.088);
				rad1 = 1.088 * (rad + rad * d) / 2;
				rad2 = 1.088 * (rad - rad * d) / 2;
				if (theShell.coarsegraininess.getValue() < 1)
					theShell.coarsegraininess.setValue(1);
				inc = theShell.coarsegraininess.getValue();
				if (theShell.reach.getValue() < 1)
					theShell.reach.setValue(1);
				start = theShell.reach.getValue() * 360;
				centre.h = where.h;
				centre.v = where.v;
				clipBoarding = false;
				m = start;
				// main procedure Drawshell
				do {
					p = (start - (start - m) * (1 - theShell.translationGradient.getValue())) / start;
					t = theShell.translation.getValue() * p;
					i = m / 360;
					fw = Math.exp(-i * Math.log(w));
					grunge = fw * Math.cos(twoPi * i);

					// the minus signs are to invert the whole snail

					xc = (theShell.handedness.getValue() == HandednessType.Left ? -1 : 1) * (rad1 * grunge);
					yc = -rad1 * t * (1 - fw);
					xr = (theShell.handedness.getValue() == HandednessType.Left ? -1 : 1) * (rad2 * grunge);
					yr = -rad2 * fw * s;

					h = (yc - yr - mny);
					g = (xc - xr - mnx);
					f = (yc + yr - mny);
					k = (xc + xr - mnx);
					theRect.top = (int) Math.round(f);
					theRect.bottom = (int) Math.round(h);
					theRect.left = (int) Math.round(g);
					theRect.right = (int) Math.round(k);
					if (theRect.left <= theRect.right) {
						if (theRect.left < margin.left)
							margin.left = theRect.left;
						if (theRect.right > margin.right)
							margin.right = theRect.right;
					} else {
						if (theRect.right < margin.left)
							margin.left = theRect.right;
						if (theRect.left > margin.right)
							margin.right = theRect.left;
					}
					if (theRect.top < margin.top)
						margin.top = theRect.top;
					if (theRect.bottom > margin.bottom)
						margin.bottom = theRect.bottom;

					// DontDraw is set by Pedigree so only Margin is measured,
					// no drawing
					if (!dontDraw)
						// picLine(myPic, theRect.left, theRect.top,
						// theRect.right, theRect.bottom);
						m -= inc;
				} while (m < 0);
			}

			public void drawTop() {
				double p;

				GoSub8000 goSub8000er = new GoSub8000() {

					public void goSub8000() {
						int xx1, xx2, yy1, yy2;
						xx1 = (int) Math.round(x1 - mnx);
						xx2 = (int) Math.round(x2 - mnx);
						yy1 = (int) Math.round(y1 - mny);
						yy2 = (int) Math.round(y2 - mny);
						if (xx1 < margin.left)
							margin.left = xx1;
						if (xx2 > margin.right)
							margin.right = xx2;
						if (yy1 < margin.top)
							margin.top = yy1;
						if (yy2 > margin.bottom)
							margin.bottom = yy2;

						// myPic.picLine(xx1, yy1, xx2, yy2);

					}
				};
				theShell.gradient.setValue(1);
				w = theShell.opening.getValue();
				d = theShell.displacement.getValue();
				inc = theShell.coarsegraininess.getValue();
				start = theShell.reach.getValue() * 360;
				rad = 100;
				theSize = 0.74;
				denom = 136 * theSize;
				mny = (-(100 / denom) * where.v);
				// the smaller or more negative the number,the lower down the
				// page
				// {mxy = 90;}
				mnx = (-(100 / denom) * where.h);

				xv1 = 0;
				yv1 = 0;
				xu1 = 0;
				yu1 = 0;

				m = start;
				do {
					p = (start - (start - m) * (1 - theShell.gradient.getValue())) / start;
					d = theShell.displacement.getValue() * p;
					i = m / 360;
					bd = 2 * Math.PI * i;
					wi = rad * Math.exp(-i * Math.log(w));
					xv2 = (theShell.handedness.getValue() == HandednessType.Left ? -1 : 1) * (-wi * Math.cos(bd));
					yv2 = wi * Math.sin(bd);
					xu2 = xv2 * d;
					yu2 = yv2 * d;
					x1 = xv1;
					x2 = xv2;
					y1 = yv1;
					y2 = yv2;
					goSub8000er.goSub8000();
					x1 = x2;
					x2 = xu2;
					y1 = y2;
					y2 = yu2;
					if (m <= 0)
						goSub8000er.goSub8000();
					x1 = x2;
					x2 = xu1;
					y1 = y2;
					y2 = yu1;
					goSub8000er.goSub8000();
					xv1 = xv2;
					yv1 = yv2;
					xu1 = xu2;
					yu1 = yu2;
					m = m - inc;
				} while (m >= 0);
			}
		};
		
		// myPic.zeroPic(where);
		if (zeroMargin) {
			margin.left = where.h;
			margin.right = where.h;
			margin.top = where.v;
			margin.bottom = where.v;
		}
		if (sideView)
			drawers.drawShell();
		else
			drawers.drawTop();
		double theScale = findTheScale(box, margin);
		height = margin.bottom - margin.top;
		width = margin.right - margin.left;
		newheight = (int) Math.round(height * theScale);
		newwidth = (int) Math.round(width * theScale);
		newtop = margin.top + (height - newheight) / 2;
		newbottom = newtop + newheight;
		newleft = margin.left + (width - newwidth) / 2;
		newright = newleft + newwidth;
		margin.setRect(newleft, newtop, newright, newbottom);
		centre.v = margin.top + (margin.bottom - margin.top) / 2;
		offSet = centre.v - where.v;
		margin.top = margin.top - offSet;
		margin.bottom = margin.bottom - offSet;
		Point offCentre = new Point();
		offCentre.v = where.v - offSet;
		centre.h = margin.left + (margin.right - margin.left) / 2;
		offSet = centre.h - where.h;
		margin.left = margin.left - offSet;
		margin.right = margin.right - offSet;
		offCentre.h = where.h - offSet;
		if (!delayedDrawing) {
			// BufferedImage snapshot = drawPic(myPic, offCentre, theShell);
			// graphics.drawImage(snapshot, null, 0, 0);
		}
		return offCentre;
	}

	/**
	 * Pic already contains its own origin, meaning the coordinates at which} it
	 * was originally drawn. Now draw it at Place}
	 * 
	 */
	private BufferedImage drawPic(Pic thisPic, Point place, SnailGenome biomorph) {

		int width = 0; // FIXME
		int height = 0; // FIXME

		int jThreshold;
		BufferedImage saveBitMap = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// GrafPtr SavePort;
		// BitMap SaveBitMap
		// GetPort(SavePort);
		// SaveBitMap := SavePort^.PortBits;
		if (biomorph.generatingCurve.getValue() < 128 || biomorph.generatingCurve.getValue() > MaxResources) {
			biomorph.generatingCurve.setValue(0);
		} else {
			if (biomorph.generatingCurve.getValue() != currentGeneratingCurve)
				changeTheBitMaps(biomorph);
		}
		currentGeneratingCurve = biomorph.generatingCurve.getValue();
		jThreshold = thisPic.lines.size() * 7 / 8;
		Graphics2D graphics = (Graphics2D) saveBitMap.getGraphics();
		for (Lin lin : thisPic.lines) {
			actualLine(graphics, thisPic, lin, jThreshold, place, jThreshold, biomorph, jThreshold, jThreshold); // sometimes
																													// rangecheck
																													// error
		}
		graphics.setStroke(new BasicStroke(1));
		return saveBitMap;
	}

	/**
	 * Given an enclosing box and a box to enclose, calculate the scaling factor
	 * required to make the enclosed box fit within the enclosing box, with an
	 * added margin of 5%.
	 * <h2>Original Documentation</h2> fix theScale here based upon Margin and
	 * box[midbox]
	 * 
	 * @return
	 */
	double findTheScale(Rect box, Rect margin) {
		int targetHeight, targetWidth, inHeight, inWidth;
		double heightScale, widthScale;
		double theScale;
		targetHeight = box.getHeight();
		targetWidth = box.getWidth();
		inHeight = margin.getHeight();
		inWidth = margin.getWidth();
		heightScale = targetHeight / inHeight;
		widthScale = targetWidth / inWidth;
		if (heightScale < widthScale)
			theScale = heightScale;
		else
			theScale = widthScale;
		return theScale * 0.95;
	}
	public void picLine(Pic myPic2, int xx1, int yy1, int xx2, int yy2) {

	}

	/** Originally (in Pascal) a nested procedure within drawPic. */
	public void quarantine(Pic thisPic, Lin lin, double theScale) {
		lin.startPt.v = (int) Math.round(theScale * (lin.startPt.v));
		lin.endPt.v = (int) Math.round(theScale * (lin.endPt.v));
		lin.startPt.h = (int) Math.round(theScale * (lin.startPt.h));
		lin.endPt.h = (int) Math.round(theScale * (lin.endPt.h));
	}

}
