package net.richarddawkins.watchmaker.genome.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

public 

class GeneBoxMouseMotionAdapter extends MouseMotionAdapter {
	enum HorizPos {
		LeftThird, MidThird, RightThird
	}

	enum VertPos {
		TopRung, MidRung, BottomRung
	}

	protected SimpleGeneBox geneBox;

	public GeneBoxMouseMotionAdapter(SimpleGeneBox geneBox) {
		this.geneBox = geneBox;
	}


	@Override
	public void mouseMoved(MouseEvent e) {

		HorizPos mouseHoriz;
		VertPos mouseVert;
		int thirdWidth = e.getComponent().getWidth() / 3;
		int thirdHeight = e.getComponent().getHeight() / 3;
		int x = e.getX();
		int y = e.getY();

		if (x < thirdWidth)
			mouseHoriz = HorizPos.LeftThird;
		else if (x < 2 * thirdWidth)
			mouseHoriz = HorizPos.MidThird;
		else
			mouseHoriz = HorizPos.RightThird;

		if (y < thirdHeight)
			mouseVert = VertPos.TopRung;
		else if (y < 2 * thirdHeight)
			mouseVert = VertPos.MidRung;
		else
			mouseVert = VertPos.BottomRung;

		switch (mouseHoriz) {
		case LeftThird:
			if(geneBox.canGooseLeftRight) {
				geneBox.setCursor(WatchmakerCursors.leftArrow);
			}
			break;
		case RightThird:
			if(geneBox.canGooseLeftRight) {
				geneBox.setCursor(WatchmakerCursors.rightArrow);
			}
			break;
		case MidThird:
			if(geneBox.canGooseUpDn) {
				switch (mouseVert) {
				
				case TopRung:
					geneBox.setCursor(WatchmakerCursors.upArrow);
					break;
				case MidRung:
					geneBox.setCursor(WatchmakerCursors.equalsArrow);
					break;
				case BottomRung:
					geneBox.setCursor(WatchmakerCursors.downArrow);
					break;
				}
			} else {
				geneBox.setCursor(WatchmakerCursors.equalsArrow);
			}
		}
	}
}