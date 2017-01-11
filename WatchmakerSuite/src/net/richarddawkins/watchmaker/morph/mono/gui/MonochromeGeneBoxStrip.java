package net.richarddawkins.watchmaker.morph.mono.gui;

import java.awt.Cursor;
import java.awt.GridBagConstraints;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.gui.MorphView;
import net.richarddawkins.watchmaker.gui.genebox.GeneBox;
import net.richarddawkins.watchmaker.gui.genebox.SimpleGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphMutagen;
import net.richarddawkins.watchmaker.morph.biomorph.genome.CompletenessType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.SpokesType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.SwellType;
import net.richarddawkins.watchmaker.morph.mono.genome.MonochromeGenome;
import net.richarddawkins.watchmaker.morph.util.Globals;
import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

public class MonochromeGeneBoxStrip extends SimpleGeneBoxStrip {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected MonochromeGenome genome;

	private void updateGeneBox(int geneBoxIndex) {
		GeneBox geneBox = (GeneBox) this.getComponent(geneBoxIndex);
		switch (geneBoxIndex) {
		case 0: // 1 in Pascal - ABC
		case 1: // 2 in Pascal - ABC
		case 2: // 3 in Pascal - ABC
		case 3: // 4 in Pascal - ABC
		case 4: // 5 in Pascal - ABC
		case 5: // 6 in Pascal - ABC
		case 6: // 7 in Pascal - ABC
		case 7: // 8 in Pascal - ABC
		case 8: // 9 in Pascal - ABC
			geneBox.setValueLabelValue(genome.getGene(geneBoxIndex));
			break;
		case 9: // 10 in Pascal - ABC
			geneBox.setValueLabelValue(genome.getSegNoGene());
			break;
		case 10: // 11 in Pascal - ABC
			geneBox.setValueLabelValue(genome.getSegDistGene());
			break;
		case 11: // 12 in Pascal - ABC
			geneBox.setCompleteness(genome.getCompletenessGene().getValue());
			break;
		case 12: // 13 in Pascal - ABC
			geneBox.setSpokes(genome.getSpokesGene().getValue());
			break;
		case 13: // 14 in Pascal - ABC
			geneBox.setValueLabelValue(genome.getTrickleGene());
			break;
		case 14: // 15 in Pascal - ABC
			geneBox.setValueLabelValue(genome.getMutSizeGene().getValue());
			break;
		case 15: // 16 in Pascal - ABC
			geneBox.setValueLabelValue(genome.getMutProbGene().getValue());
			break;
		}
		this.repaint();
	}

	public void setGenome(Genome genome) {
		this.genome = (MonochromeGenome) genome;
		for (int j = 0; j < this.getComponentCount(); j++)
			updateGeneBox(j);
	}

	public MonochromeGeneBoxStrip(MorphConfig config, boolean engineeringMode) {
		super(config, engineeringMode);
		int numberOfGeneBoxes = config.getGeneBoxCount();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		constraints.gridy = 0;
		for (int i = 0; i < numberOfGeneBoxes; i++) {
			boolean showPositive = false;
			if(i == 9 || i == 10 || i == 13 || i == 14 || i == 15)
				showPositive = true;
			GeneBox geneBox = new GeneBox(this, i, i < 9, showPositive);
			constraints.gridx = i;
			this.add(geneBox, constraints);
		}

	}

	@Override
	public Genome getGenome() {
		return genome;
	}

	@Override
	public void goose(int geneBoxNo, Cursor cursor) {
		switch (geneBoxNo) {
		case 0: // 1 in Pascal
		case 1: // 2 in Pascal
		case 2: // 3 in Pascal
		case 3: // 4 in Pascal
		case 4: // 5 in Pascal
		case 5: // 6 in Pascal
		case 6: // 7 in Pascal
		case 7: // 8 in Pascal
			if (cursor.equals(WatchmakerCursors.leftArrow)) {
				BiomorphMutagen.addToGene(genome, geneBoxNo, -genome.getMutSizeGene().getValue());
			} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
				BiomorphMutagen.addToGene(genome, geneBoxNo, genome.getMutSizeGene().getValue());
			} else if (cursor.equals(WatchmakerCursors.upArrow)) {
				genome.setDGene(geneBoxNo, SwellType.Swell);
			} else if (cursor.equals(WatchmakerCursors.equalsArrow)) {
				genome.setDGene(geneBoxNo, SwellType.Same);
			} else if (cursor.equals(WatchmakerCursors.downArrow)) {
				genome.setDGene(geneBoxNo, SwellType.Shrink);
			}
			break;
		case 8: // 9 in Pascal
			if (cursor.equals(WatchmakerCursors.leftArrow)) {
				BiomorphMutagen.decrementGene(genome, geneBoxNo);
			} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
				long sizeWorry = (long) ((genome.getSegNoGene() + 1) * Math.pow(2, genome.getGene(8)));
				if (sizeWorry <= Globals.worryMax)
					BiomorphMutagen.incrementGene(genome, geneBoxNo);
			} else if (cursor.equals(WatchmakerCursors.upArrow)) {
				genome.setDGene(geneBoxNo, SwellType.Swell);
			} else if (cursor.equals(WatchmakerCursors.equalsArrow)) {
				genome.setDGene(geneBoxNo, SwellType.Same);
			} else if (cursor.equals(WatchmakerCursors.downArrow)) {
				genome.setDGene(geneBoxNo, SwellType.Shrink);
			}
			break;
		case 9: // 10 in Pascal
			if (cursor.equals(WatchmakerCursors.leftArrow)) {
				BiomorphMutagen.decrementSegNoGene(genome);
			} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
				long sizeWorry = (long) ((genome.getSegNoGene() + 1) * Math.pow(2, genome.getGene(8)));
				if (sizeWorry <= Globals.worryMax) {
					BiomorphMutagen.incrementSegNoGene(genome);
				}
			}
			break;
		case 10: // 11 in Pascal
			if (cursor.equals(WatchmakerCursors.leftArrow)) {
				genome.setSegDistGene(genome.getSegDistGene() - genome.getTrickleGene());
			} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
				genome.setSegDistGene(genome.getSegDistGene() + genome.getTrickleGene());
			} else if (cursor.equals(WatchmakerCursors.upArrow)) {
				genome.setDGene(9, SwellType.Swell);
			} else if (cursor.equals(WatchmakerCursors.equalsArrow)) {
				genome.setDGene(9, SwellType.Same);
			} else if (cursor.equals(WatchmakerCursors.downArrow)) {
				genome.setDGene(9, SwellType.Shrink);
			}
			break;
		case 11: // 12 in Pascal
			if (cursor.equals(WatchmakerCursors.leftArrow)) {
				genome.getCompletenessGene().setValue(CompletenessType.Single);
			} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
				genome.getCompletenessGene().setValue(CompletenessType.Double);
			}
			break;
		case 12: // 13 in Pascal
			if (cursor.equals(WatchmakerCursors.leftArrow)) {
				genome.getSpokesGene().setValue(SpokesType.NorthOnly);
			} else if (cursor.equals(WatchmakerCursors.equalsArrow)) {
				genome.getSpokesGene().setValue(SpokesType.NSouth);
			} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
				genome.getSpokesGene().setValue(SpokesType.Radial);
			}
			break;
		case 13: // 14 in Pascal
			if (cursor.equals(WatchmakerCursors.leftArrow)) {
				if (genome.getTrickleGene() > 0)
					BiomorphMutagen.decrementTrickleGene((BiomorphGenome) genome);
			} else if (cursor.equals(WatchmakerCursors.equalsArrow)) {
				genome.getSpokesGene().setValue(SpokesType.NSouth);
			} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
				BiomorphMutagen.incrementTrickleGene(genome);
			}
			break;
		case 14: // 15 in Pascal
			if (cursor.equals(WatchmakerCursors.leftArrow)) {

				if (genome.getMutSizeGene().getValue() > 1)
					BiomorphMutagen.decrementMutSizeGene(genome);
			} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
				BiomorphMutagen.incrementMutSizeGene(genome);

			}
			break;
		case 15: // 16 in Pascal
			if (cursor.equals(WatchmakerCursors.leftArrow)) {
				if (genome.getMutProbGene().getValue() > 1)
					BiomorphMutagen.decrementMutProbGene(genome);

			} else if (cursor.equals(WatchmakerCursors.rightArrow)) {
				if (genome.getMutProbGene().getValue() < 100)
					BiomorphMutagen.incrementMutProbGene(genome);
			}
			break;
		default:
		}
		if(genome.getGene(8) < 1) genome.setGene(8, 1);
		if(genome.getSegNoGene() < 1) genome.setSegNoGene(1);
		this.updateGeneBox(geneBoxNo);
		this.repaint();
		MorphView selectedMorphView = (MorphView) config.getMorphViewsTabbedPane().getSelectedComponent();
		selectedMorphView.repaint();

	}
}
