package net.richarddawkins.watchmaker.gui.genebox;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morph.common.BiomorphGenome;
import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;



public class GradientPanel extends JPanel {
	protected int dGeneIndex;
	public int getdGeneIndex() {
		return dGeneIndex;
	}

	public void setdGeneIndex(int dGeneIndex) {
		this.dGeneIndex = dGeneIndex;
	}

	public GeneBox getGeneBox() {
		return geneBox;
	}

	public void setGeneBox(GeneBox geneBox) {
		this.geneBox = geneBox;
	}
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
			BiomorphGenome genome = (BiomorphGenome)geneBox
					.getGeneBoxStrip().getGenome();
			switch(genome.getDGene(dGeneIndex)) {
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
	
	  
}
