package net.richarddawkins.watchmaker.morphs.mono.genebox.swing;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.image.ClassicImageLoader;
import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.morphs. mono.genebox.IntegerGradientGeneBox;
import net.richarddawkins.watchmaker.morphs.mono.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.SwellType;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingIntegerGeneBox;
import net.richarddawkins.watchmaker.swing.images.AWTClassicImage;

public class SwingIntegerGradientGeneBox extends SwingIntegerGeneBox implements IntegerGradientGeneBox {
	static BufferedImage BULLET;
	static {
	    ClassicImageLoader loader = ClassicImageLoaderService.getInstance().getClassicImageLoader();
	    AWTClassicImage classicImage = (AWTClassicImage)loader.getPicture("BulletChar165_ALAN_0015_5x5");
		BULLET = classicImage.getImage();
	}

	protected SwellType gradient = null;
	private static final long serialVersionUID = 1631776161817013885L;

	public SwingIntegerGradientGeneBox(AppData appData) {
		super(appData);
		JPanel gradientPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				if (gradient != null) {
					switch (gradient) {
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
		};
		this.add(gradientPanel, BorderLayout.WEST);

	}

	@Override
	public void setGene(Gene newGene) {
		super.setGene(newGene);
		IntegerGradientGene gene = (IntegerGradientGene) newGene;
		setGradient(gene.getGradient());
	}
	
	@Override
	public void setEngineeringMode() {
		super.setEngineeringMode(GeneBoxType.leftRightUpDownEquals);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("gradient")) {
			this.setGradient((SwellType) evt.getNewValue());
			repaint();
		} else {
			super.propertyChange(evt);
		}

	}

	@Override
	public void setGradient(SwellType swellType) {
		this.gradient = swellType;
		repaint();
	}
}
