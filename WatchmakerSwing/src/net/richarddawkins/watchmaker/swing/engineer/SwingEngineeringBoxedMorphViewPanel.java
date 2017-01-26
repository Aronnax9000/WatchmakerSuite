package net.richarddawkins.watchmaker.swing.engineer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Boxes;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.resourceloader.Messages;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingBoxedMorphViewPanel;

public class SwingEngineeringBoxedMorphViewPanel extends SwingBoxedMorphViewPanel implements PropertyChangeListener {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.engineer.SwingEngineeringBoxedMorphViewPanel");

	SwingEngineeringMorphView engineeringWatchmakerPanel;
	MouseAdapter mouseAdapter;

	public SwingEngineeringBoxedMorphViewPanel(SwingEngineeringMorphView engineeringWatchmakerPanel) {
		super(engineeringWatchmakerPanel.getAppData());
		setCursor(WatchmakerCursors.hypodermic);
		this.engineeringWatchmakerPanel = engineeringWatchmakerPanel;
		boxes = new Boxes(1, 1);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				logger.info("Showing hypodermic message dialog");
				Object[] options = { "Okay" };
			    JOptionPane.showOptionDialog(
			    		engineeringWatchmakerPanel,
			            new HypodermicWarning(),
			            null, 
			            JOptionPane.DEFAULT_OPTION, 
			            JOptionPane.PLAIN_MESSAGE,
			            null, options, options[0]);
			}
		});
	}

	
	private static final long serialVersionUID = -5519820942516604540L;

	public void seed(Morph morph) {
		if (!boxedMorphVector.isEmpty()) {
			for (BoxedMorph boxedMorph : boxedMorphVector.getBoxedMorphs()) {
				boxedMorph.getMorph().removePropertyChangeListener(this);
			}
			boxedMorphVector.removeAllElements();
		}
		morph.addPropertyChangeListener(this);
		boxedMorphVector.add(new BoxedMorph(boxes, morph, 0));
		GeneBoxStrip geneBoxStrip = (GeneBoxStrip) this.engineeringWatchmakerPanel.getUpperStrip();
		geneBoxStrip.setGenome(morph.getGenome());
	}

	@Override
	public void updateModel(Dim size) {
		this.getBoxedMorphVector().getBoxedMorph(0).setPosition(boxes.getMidPoint(size, 0));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if (evt.getPropertyName().equals("phenotype")) {
			getBoxedMorphVector().getBoxedMorph(0).getMorph().setImage(null);
			repaint();
		}
	}

	class HypodermicWarning extends JPanel {
		private static final long serialVersionUID = 1L;
		public HypodermicWarning() {
			setLayout(new GridBagLayout());	
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.ipadx = 16;
			constraints.ipady = 16;
			JLabel imageLabel = new JLabel(new ImageIcon(
					ClassicImageLoader.getPicture("Hypodermic_PICT_03937_16x16")
					.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT)));
			add(imageLabel, constraints);
			JLabel warningLabel = new JLabel(Messages.getMessages().getString("EngineeringHypodermicWarning"));
			constraints.gridx++;
			add(warningLabel, constraints);
		}
		
	}
}
