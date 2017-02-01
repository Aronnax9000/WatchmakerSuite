package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphVector;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.morphview.MorphView;
import net.richarddawkins.watchmaker.morphview.MorphViewWidget;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.components.SwingScaleSlider;
import net.richarddawkins.watchmaker.swing.drawer.SwingMorphDrawer;

public abstract class SwingMorphView extends JPanel implements MorphView, PropertyChangeListener {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.morphview.SwingMorphView");

	private static final long serialVersionUID = 5555392236002752598L;
	protected AppData appData;

	protected BoxedMorphVector boxedMorphVector = new BoxedMorphVector();

	protected BoxManager boxes;
	protected final JPanel centrePanel;

	protected String icon;

	protected MorphViewWidget lowerStrip;

	protected MorphDrawer morphDrawer;

	protected boolean showBoxes = true;

	protected String toolTip;

	protected MorphViewWidget upperStrip;

	public SwingMorphView(AppData appData) {
		this.appData = appData;
		this.setLayout(new BorderLayout());
       	this.centrePanel = new JPanel();
       	this.setPreferredSize(new Dimension(640, 480));
       	this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(centrePanel, BorderLayout.CENTER);
		centrePanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

				processMouseMotion(SwingGeom.toWatchmakerPoint(e.getPoint()));
			}
		});


		centrePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				boxClicked(SwingGeom.toWatchmakerPoint(e.getPoint()));
			}
		});	
		PhenotypeDrawer phenotypeDrawer = appData.getPhenotypeDrawer();
		morphDrawer = new SwingMorphDrawer(phenotypeDrawer);
		phenotypeDrawer.getDrawingPreferences().addPropertyChangeListener(this);
	}

	public SwingMorphView(AppData appData, String icon, String name) {
		this(appData);
		this.setIcon(icon);
		this.setName(name);

	}

	public SwingMorphView(AppData appData, String icon, String name, boolean engineeringMode) {
		this(appData, icon, name);
		GeneBoxStrip geneBoxStrip = appData.newGeneBoxStrip(engineeringMode);
		setUpperStrip(geneBoxStrip);
		SwingScaleSlider scaleSlider = new SwingScaleSlider(appData.getPhenotypeDrawer().getDrawingPreferences());
		setLowerStrip(scaleSlider);
	}

	public AppData getAppData() {
		return appData;
	}

	@Override
	public BoxedMorphVector getBoxedMorphVector() {
		return boxedMorphVector;
	}

	@Override
	public BoxManager getBoxes() {
		return boxes;
	}

	@Override
	public JPanel getCentrePanel() {
		return centrePanel;
	}

	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public MorphViewWidget getLowerStrip() {
		return lowerStrip;
	}

	@Override
	public MorphDrawer getMorphDrawer() {
		return morphDrawer;
	}

	@Override
	public Morph getMorphOfTheHour() {
		return null;
	}

	@Override
	public Vector<Morph> getMorphs() {
		return boxedMorphVector.getMorphs();
	}

	@Override
	public String getToolTip() {
		return toolTip;
	}

	@Override
	public MorphViewWidget getUpperStrip() {
		return upperStrip;
	}

	@Override
	public boolean isShowBoxes() {
		return showBoxes;
	}

	@Override
	public void paintMorphViewPanel(Graphics2D g2, Dim size) {
		updateModel(size);
		Vector<Integer> backgroundColors = new Vector<Integer>();
		for (int i = 0; i < this.boxes.getBoxCount(); i++) {
			BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(i);
			if (boxedMorph != null) {
				backgroundColors.add(boxedMorph.getMorph().getPhenotype().getBackgroundColor());
			} else {
				backgroundColors.add(-1);
			}
		}
		if (showBoxes) {
			BoxesDrawer boxesDrawer = appData.getBoxesDrawer();
			boxesDrawer.draw(g2, size, boxes, boxedMorphVector.getBoxedMorphs().size() == 1, backgroundColors);
		}
		for (BoxedMorph boxedMorph : boxedMorphVector.getBoxedMorphs()) {
			morphDrawer.setSize(boxes.getBoxSize(size));
			morphDrawer.draw(boxedMorph, g2, size);
		}
	}

	public void propertyChange(PropertyChangeEvent event) {
		String propertyName = event.getPropertyName();
		switch (propertyName) {
		case "showBoundingBoxes":
		case "scale":
		case "phenotype":
			logger.info("SwingMorphViewPanel propertyChange:" + propertyName);
			for (Morph morph : getMorphs()) {
				morph.setImage(null);
			}
			repaint();
			break;
		default:
		}
	}

	@Override
	public void setAppData(AppData appData) {
		this.appData = appData;
	}

	@Override
	public void setBoxedMorphVector(BoxedMorphVector boxedMorphVector) {
		this.boxedMorphVector = boxedMorphVector;
	}

	@Override
	public void setBoxes(BoxManager boxes) {
		this.boxes = boxes;
	}



	abstract protected void boxClicked(Point point);

	abstract protected void processMouseMotion(Point myPt);

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public void setLowerStrip(MorphViewWidget lowerStrip) {
		if (this.lowerStrip != null)
			this.remove((JComponent) this.lowerStrip);
		this.lowerStrip = lowerStrip;
		if (this.lowerStrip != null)
			this.add((JComponent) this.lowerStrip, BorderLayout.PAGE_END);
	}

	@Override
	public void setMorphDrawer(MorphDrawer morphDrawer) {
		this.morphDrawer = morphDrawer;
	}

	@Override
	public void setShowBoxes(boolean showBoxes) {
		this.showBoxes = showBoxes;
	}

	@Override
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	@Override
	public void setUpperStrip(MorphViewWidget upperStrip) {
		if (this.upperStrip != null)
			this.remove((JPanel) this.upperStrip);
		this.upperStrip = upperStrip;
		if (this.upperStrip != null)
			this.add((JPanel) this.upperStrip, BorderLayout.PAGE_START);

	}
}
