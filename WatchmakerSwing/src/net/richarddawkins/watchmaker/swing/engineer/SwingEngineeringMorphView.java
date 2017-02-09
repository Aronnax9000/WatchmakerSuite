package net.richarddawkins.watchmaker.swing.engineer;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.resourceloader.Messages;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingEngineeringMorphView extends SwingMorphView {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.engineer.SwingEngineeringMorphView");

	public SwingEngineeringMorphView(AppData appData, Morph morph) {
		super(appData, "Hypodermic_PICT_03937_16x16", "Engineering", true, appData.isGeneBoxToSide());
		boxedMorphVector.setBoxes(new GridBoxManager(1, 1));
		((Component)this.getCentrePanel()).setCursor(WatchmakerCursors.hypodermic);
		MorphConfig config = appData.getMorphConfig();
		
		
		if(morph != null) {
			Morph copy = config.newMorph();
			Genome genome = config.newGenome();
			morph.getGenome().copy(genome);
			copy.setGenome(genome);
			seed(copy);
		} else {
			morph = config.newMorph(0);
			seed(morph);
		}
		
	}


    @Override
    public Morph getMorphOfTheHour() {
    	return boxedMorphVector
    			.getBoxedMorph(0)
    			.getMorph();
    }
	@Override
	public void seed(Morph morph) {
		if (!boxedMorphVector.isEmpty()) {
			for (BoxedMorph boxedMorph : boxedMorphVector.getBoxedMorphs()) {
				boxedMorph.getMorph().removePropertyChangeListener(this);
			}
			boxedMorphVector.removeAllElements();
		}
		morph.addPropertyChangeListener(this);
		BoxManager boxes = boxedMorphVector.getBoxes();

		BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, 0); 
		boxedMorphVector.add(boxedMorph);
		pcs.firePropertyChange("genome", null, 
				boxedMorph.getMorph().getGenome());

	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8224824610112892419L;

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
	@Override
	protected void boxClicked(Point point) {
		logger.info("Showing hypodermic message dialog");
		Object[] options = { "Okay" };
	    JOptionPane.showOptionDialog(
	    		this,
	            new HypodermicWarning(),
	            null, 
	            JOptionPane.DEFAULT_OPTION, 
	            JOptionPane.PLAIN_MESSAGE,
	            null, options, options[0]);
		
	}
	@Override
	protected void processMouseMotion(Point myPt, Dim size) {
		// TODO Auto-generated method stub
		
	}


}
