package net.richarddawkins.watchmaker.gui.genebox;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morph.BiomorphGenome;
import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;

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
	protected int dGeneIndex;
	protected GeneBox geneBox;
	public GradientPanel(GeneBox geneBox, int dGeneIndex) {
		this.geneBox = geneBox;
		this.dGeneIndex = dGeneIndex;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 273011307434890144L;

	static BufferedImage BULLET;
	static {
		 BULLET = ClassicImageLoader
				.getPicture("BulletChar165_ALAN_0015_5x5")
				.getImage();

	}
	

	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		if(geneBox.hasSwell) {
			BiomorphGenome genome 
			  = (BiomorphGenome)
			  geneBox.getGeneBoxStrip().getGenome();
			
			switch(genome.getDGene(dGeneIndex)) {
			case Swell: 
				System.out.println("Swell ");
				g2.drawImage(BULLET, 0, (getSize().height - BULLET.getHeight())/2, null);
				break;
			case Shrink:
				System.out.println("Shrink");
				g2.drawImage(BULLET, 0, getSize().height - BULLET.getHeight(), null);
				break;
			default:
				System.out.println("Same");
			}
		}
			
		
	}
	
	public int getDGeneIndex() {
		return dGeneIndex;
	}

	public GeneBox getGeneBox() {
		return geneBox;
	}

	  
}
