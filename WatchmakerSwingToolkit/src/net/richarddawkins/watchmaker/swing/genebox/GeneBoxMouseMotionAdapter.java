package net.richarddawkins.watchmaker.swing.genebox;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;

public class GeneBoxMouseMotionAdapter extends MouseMotionAdapter {

	enum HorizPos {
		LeftThird, MidThird, RightThird, LeftHalf, RightHalf
	}

	enum VertPos {
		TopRung, MidRung, BottomRung
	}

	public GeneBoxMouseMotionAdapter(GeneBoxType geneBoxType) {
		this.geneBoxType = geneBoxType;
	}

	protected GeneBoxType geneBoxType;

	@Override
	public void mouseMoved(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		HorizPos mouseHoriz;
		
		VertPos mouseVert = null;

		if (geneBoxType == GeneBoxType.leftRightOnly) {
			int halfWidth = e.getComponent().getWidth() / 2;
			if (x < halfWidth)
				mouseHoriz = HorizPos.LeftHalf;
			else
				mouseHoriz = HorizPos.RightHalf;
		} else {
			int thirdWidth = e.getComponent().getWidth() / 3;
			if (x < thirdWidth)
				mouseHoriz = HorizPos.LeftThird;
			else if (x < 2 * thirdWidth)
				mouseHoriz = HorizPos.MidThird;
			else
				mouseHoriz = HorizPos.RightThird;
			
			if(geneBoxType == GeneBoxType.leftRightUpDownEquals) {
				int thirdHeight = e.getComponent().getHeight() / 3;
				if (y < thirdHeight)
					mouseVert = VertPos.TopRung;
				else if (y < 2 * thirdHeight)
					mouseVert = VertPos.MidRung;
				else
					mouseVert = VertPos.BottomRung;
			}
		}

		switch (mouseHoriz) {
		case LeftThird:
		case LeftHalf:
			e.getComponent().setCursor(WatchmakerCursors.leftArrow);
			break;
		case RightThird:
		case RightHalf:
			e.getComponent().setCursor(WatchmakerCursors.rightArrow);
			break;
		case MidThird:
			switch (mouseVert) {
			case TopRung:
				e.getComponent().setCursor(WatchmakerCursors.upArrow);
				break;
			case BottomRung:
				e.getComponent().setCursor(WatchmakerCursors.downArrow);
				break;
			case MidRung:
			default:
				e.getComponent().setCursor(WatchmakerCursors.equalsSign);
			}
		}
	}
}