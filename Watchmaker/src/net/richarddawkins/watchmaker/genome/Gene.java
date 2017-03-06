package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeListener;
import java.nio.ByteBuffer;

public interface Gene extends GeneManipulationListener {
	Genome getGenome();
	void setGenome(Genome genome);
	String getName();
	void setName(String name);
	void copy(Gene gene);
	void removePropertyChangeListener(PropertyChangeListener listener);
	void addPropertyChangeListener(PropertyChangeListener listener);
	int getGooseSize();
	double getDoubleGooseSize();
	void readValueFromByteBuffer(ByteBuffer byteBuffer);
	void writeValueToByteBuffer(ByteBuffer byteBuffer);
	void readIndexedValueFromByteBuffer(ByteBuffer byteBuffer, int index);
	void writeIndexedValueToByteBuffer(ByteBuffer byteBuffer, int index);
    boolean genomicallyEquals(Gene gene);
    void kill();
	
	
}
