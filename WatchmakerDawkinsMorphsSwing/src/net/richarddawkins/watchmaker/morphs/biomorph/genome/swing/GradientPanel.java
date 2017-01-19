package net.richarddawkins.watchmaker.morphs.biomorph.genome.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morphs.bio.genome.GradientGene;
import net.richarddawkins.watchmaker.swing.genebox.GeneBox;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;

/**
 * A GradientPanel displays gradient genes (Shrink/Swell/Same.)
 * A GraidentPanel holds a reference to the GeneBox it is 
 * displayed in,
 * and a separate reference to the gradient gene index in the
 * underlying Genome. In this way, a gradient panel can display
 * a gradient for a gene whose numeric value is represented in 
 * a different GeneBox, or not at all.
 * @author Alan
 *
 */

public class GradientPanel extends JPanel {
	
	protected GeneBox geneBox;
	public GradientPanel(GeneBox geneBox) {
		this.geneBox = geneBox;
	}

	private static final long serialVersionUID = 273011307434890144L;

	static BufferedImage BULLET;
	static {
		 BULLET = ClassicImageLoader
				.getPicture("BulletChar165_ALAN_0015_5x5")
				.getImage();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		GradientGene gradientGene = (GradientGene)geneBox.getGene();
		if(gradientGene != null) {
			switch(gradientGene.getGradient()) {
			case Swell: 
				g2.drawImage(BULLET, 0, 0, null);
				break;
			case Shrink:
				g2.drawImage(BULLET, 0, getSize().height - BULLET.getHeight(), null);
				break;
			default:
			}
		}
	}

	public GeneBox getGeneBox() {
		return geneBox;
	}

	  
}
