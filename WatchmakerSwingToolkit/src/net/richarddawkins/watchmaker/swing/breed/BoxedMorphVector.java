package net.richarddawkins.watchmaker.swing.breed;

import java.util.Iterator;
import java.util.Vector;

import net.richarddawkins.watchmaker.geom.BoxedMorph;

public class BoxedMorphVector {
	protected Vector<BoxedMorph> boxedMorphs = new Vector<BoxedMorph>();
	
	public boolean isEmpty() {
		return boxedMorphs.isEmpty();
	}
	
	public void add(BoxedMorph boxedMorph) {
		boxedMorphs.add(boxedMorph);
	}
	public void remove(BoxedMorph boxedMorph) {
		boxedMorphs.remove(boxedMorph);
	}
	public void removeAllElements() {
		boxedMorphs.removeAllElements();
	}
	public Iterator<BoxedMorph> iterator() {
		return boxedMorphs.iterator();
	}
	
	public BoxedMorph getBoxedMorph(int boxNo) {
		for(BoxedMorph boxedMorph: boxedMorphs) 
			if(boxedMorph.boxNo == boxNo)
				return boxedMorph;
		return null;
	}
	
	public Vector<BoxedMorph> getBoxedMorphs() {
		return boxedMorphs;
	}

	public void setBoxedMorphs(Vector<BoxedMorph> boxedMorphs) {
		this.boxedMorphs = boxedMorphs;
	}
}
