package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeListener;
import java.nio.ByteBuffer;

public interface Genome extends PropertyChangeListener {
	void copy(Genome targetGenome);


	Gene[] toGeneArray();
	Gene getGene(int geneBoxIndex);
	void addGenomeChangeListener(GenomeChangeListener listener);
	void removeGenomeChangeListener(GenomeChangeListener listener);


	void readFromByteBuffer(ByteBuffer byteBuffer);


	void writeToByteBuffer(ByteBuffer byteBuffer);


    boolean genomicallyEquals(Genome genome);


    int getSizeInBytes();


    void kill();

}
