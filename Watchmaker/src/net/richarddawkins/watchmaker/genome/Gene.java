package net.richarddawkins.watchmaker.genome;

import java.beans.PropertyChangeListener;
import java.nio.ByteBuffer;

public interface Gene extends GeneManipulationListener {
	void addPropertyChangeListener(PropertyChangeListener listener);
	void copy(Gene gene);
	boolean genomicallyEquals(Gene gene);
	double getDoubleGooseSize();
	Genome getGenome();
	int getGooseSize();
	String getName();
	void kill();
	void readIndexedValueFromByteBuffer(ByteBuffer byteBuffer, int index);
	void readValueFromByteBuffer(ByteBuffer byteBuffer);
	void removePropertyChangeListener(PropertyChangeListener listener);
	void setGenome(Genome genome);
	void setName(String name);
    void writeIndexedValueToByteBuffer(ByteBuffer byteBuffer, int index);
    void writeValueToByteBuffer(ByteBuffer byteBuffer);
}
