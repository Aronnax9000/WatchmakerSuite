package net.richarddawkins.watchmaker.gui.genebox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morph.mono.genome.CompletenessType;
import net.richarddawkins.watchmaker.morph.mono.genome.SpokesType;
import net.richarddawkins.watchmaker.morph.mono.genome.SwellType;
import net.richarddawkins.watchmaker.resourceloader.Messages;
import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

public class GeneBox extends JPanel {
	private static final long serialVersionUID = 1L;

	protected boolean hasCompleteness = false;
	protected boolean hasSpokes = false;
	protected boolean hasSwell = false;

	protected int geneBoxNo;
	protected CompletenessType completeness;
	protected SpokesType spokes;
	protected SwellType swell;
	protected boolean negative;

	protected GeneBoxStrip geneBoxStrip;

	protected GradientPanel gradientPanel;
	protected JPanel valuePanel = new JPanel();
	protected JLabel valueLabel = new JLabel("X");

	public static int GenesHeight = 20;


	
	/**
	 * 
	 * @return true if the GeneBox shadows a Completeness (Double, Single) type
	 *         gene, false otherwise.
	 */
	public CompletenessType isCompleteness() {
		return completeness;
	}

	public void setCompleteness(CompletenessType completeness) {
		this.completeness = completeness;
		String labelString = "";
		switch (completeness) {
		case Single:
			labelString = Messages.getMessages().getString("STRO_12947,0");
			break;
		case Double:
			labelString = Messages.getMessages().getString("STRO_12947,1");
			break;
		}
		this.valueLabel.setText(labelString);
	}

	public SpokesType getSpokesType() {
		return spokes;
	}

	public void setSpokes(SpokesType spokes) {
		this.spokes = spokes;

		String labelString = "";

		switch (spokes) {
		case NorthOnly:
			labelString = Messages.getMessages().getString("STRO_12947,2");
			break;
		// DrawString(SingleString);
		case NSouth:
			labelString = Messages.getMessages().getString("STRO_12947,3");
			// DrawString(UpDnString);
			break;
		case Radial:
			labelString = Messages.getMessages().getString("STRO_12947,4");
			// DrawString(RadialString);
			break;
		}
		this.valueLabel.setText(labelString);

	}

	public boolean isNegative() {
		return negative;
	}

	public void setNegative(boolean negative) {
		this.negative = negative;
	}

	public boolean isHasCompleteness() {
		return hasCompleteness;
	}

	public void setHasCompleteness(boolean hasCompleteness) {
		this.hasCompleteness = hasCompleteness;
	}

	public boolean isHasSpokes() {
		return hasSpokes;
	}

	public void setHasSpokes(boolean hasSpokes) {
		this.hasSpokes = hasSpokes;
	}

	public boolean isHasSwell() {
		return hasSwell;
	}

	public void setHasSwell(boolean hasSwell) {
		this.hasSwell = hasSwell;
	}

	public GeneBoxStrip getGeneBoxStrip() {
		return geneBoxStrip;
	}

	public void setGeneBoxStrip(GeneBoxStrip geneBoxStrip) {
		this.geneBoxStrip = geneBoxStrip;
	}

	public void setValueLabelValue(int value) {
		valueLabel.setText(new Integer(value).toString());
	}

	public GeneBox(GeneBoxStrip geneBoxStrip, int dGeneIndex, boolean hasSwell) {
		this.geneBoxStrip = geneBoxStrip;
		this.hasSwell = hasSwell;
		this.geneBoxNo = dGeneIndex;
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setLayout(new BorderLayout());
		if (hasSwell) {
			gradientPanel = new GradientPanel(this, dGeneIndex);
			this.add(gradientPanel, BorderLayout.WEST);
		}
		this.add(valuePanel, BorderLayout.CENTER);
		valuePanel.add(valueLabel);
		if (this.getGeneBoxStrip().engineeringMode) {
			this.addMouseMotionListener(new GeneBoxMouseMotionListener(this));
		}
	}

}

class GeneBoxMouseListener extends MouseAdapter {
	protected GeneBox geneBox;	
	public GeneBoxMouseListener(GeneBox geneBox) {
		this.geneBox = geneBox;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Cursor cursor = geneBox.getCursor();
		geneBox.geneBoxStrip.goose(geneBox.geneBoxNo, cursor);
	}
}

class GeneBoxMouseMotionListener extends MouseMotionAdapter {
	enum HorizPos {
		LeftThird, MidThird, RightThird
	}

	enum VertPos {
		TopRung, MidRung, BottomRung
	}

	protected GeneBox geneBox;

	public GeneBoxMouseMotionListener(GeneBox geneBox) {
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
			geneBox.setCursor(WatchmakerCursors.leftArrow);
			break;
		case RightThird:
			geneBox.setCursor(WatchmakerCursors.rightArrow);
			break;
		case MidThird:
			switch (mouseVert) {
			// IF (j <= 9) OR (j = 11) THEN
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
			// ELSE
			// SetCursor(CursList[EqCursor]^^);
		}
	}
}
