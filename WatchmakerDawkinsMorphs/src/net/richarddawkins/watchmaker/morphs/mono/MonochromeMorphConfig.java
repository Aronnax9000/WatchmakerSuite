package net.richarddawkins.watchmaker.morphs.mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.GridBoxManager;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.morphs.mono.embryo.MonochromeEmbryology;
import net.richarddawkins.watchmaker.morphs.mono.genome.BiomorphGenomeFactory;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenome;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenomeFactory;
import net.richarddawkins.watchmaker.morphs.mono.genome.mutation.MonochromeAllowedMutations;
import net.richarddawkins.watchmaker.morphs.mono.genome.mutation.MonochromeMutagen;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoPic;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class MonochromeMorphConfig extends SimpleMorphConfig {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.mono.MonochromeMorphConfig");

	@Override
	public Genome newGenome() {
		return new MonochromeGenome();
	}

	public MonochromeMorphConfig() {
		this.setStartingMorphBasicType(BiomorphGenomeFactory.BASIC_TREE);
		this.setAllowedMutations(new MonochromeAllowedMutations());
		this.setMutagen(new MonochromeMutagen(allowedMutations));
		this.setGenomeFactory(new MonochromeGenomeFactory(allowedMutations));
		this.setEmbryology(new MonochromeEmbryology());
	}

	@Override
	public Phenotype newPhenotype() {
		return new MonoPic();
	}
	
	@Override
	public Morph newMorph() {
		Morph morph = new MonochromeMorph();
		wireMorphEvents(morph);
		return morph;
	}
	@Override
	public Vector<BoxedMorphCollection> getAlbums()  {
		Vector<BoxedMorphCollection> albums = new Vector<BoxedMorphCollection>();
		String resourcePackage = "/net/richarddawkins/watchmaker/savedanimals/monochrome/";
		String[] names = new String[] {
				"Handkerchief with bows",
				"Stunted",
				"Chinese character",
				"Exhibition zoo",
				"Alphabet zoo"
		};
		for(String name: names) {
			logger.info("Loading:" + name);
			InputStream is =
			MonochromeGenomeFactory.class.getResourceAsStream(resourcePackage + name);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while(true) {
				int readByte = -1;
				try {
					readByte = is.read();
				} catch (IOException e) {
					logger.severe("While reading from saved animal file " + name + ":" + e.getMessage());
				}
				if(readByte == -1) {
					break;
				}
				baos.write(readByte);
			}
			ByteBuffer byteBuffer = ByteBuffer.wrap(baos.toByteArray());
//			byteBuffer.order(ByteOrder.BIG_ENDIAN);
			
			BoxedMorphCollection album = new BoxedMorphCollection();
			BoxManager boxManager = new GridBoxManager(5, 12);
			album.setBoxes(boxManager);
			album.setName(name);
			int boxNo = 0;
			while(byteBuffer.hasRemaining() && boxNo < 60) {
				Genome genome = new MonochromeGenome();
				genome.readFromByteBuffer(byteBuffer);
				Morph morph = newMorph();
				morph.setGenome(genome);
				logger.info(boxNo + " Genome:" + genome.toString());
				BoxedMorph boxedMorph = new BoxedMorph(boxManager, morph, boxNo++);
				album.add(boxedMorph);
			}
			logger.info("Album " + name + " contained " + boxNo + " genomes.");
			album.setSelectedBoxedMorph(album.getBoxedMorph(0));
			albums.add(album);
		}
		
		return albums;
	}

	
}
