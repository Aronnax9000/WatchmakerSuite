package net.richarddawkins.watchmaker.morph.draw;

import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.Pedigree;

public class BoxedMorphCollection {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morph.draw.BoxedMorphVector");

	protected Vector<BoxedMorph> boxedMorphs = new Vector<BoxedMorph>();
	
	protected BoxedMorph selectedBoxedMorph = null;
	protected String name;
	public int size() { return boxedMorphs.size(); }
	protected BoxManager boxes;
	
	public BoxManager getBoxes() {
		return boxes;
	}

	public void setBoxes(BoxManager boxes) {
		this.boxes = boxes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BoxedMorph getSelectedBoxedMorph() {
		return selectedBoxedMorph;
	}

	public void setSelectedBoxedMorph(BoxedMorph selectedBoxedMorph) {
		this.selectedBoxedMorph = selectedBoxedMorph;
	}

	public Vector<Morph> getMorphs() {
		Vector<Morph> morphs = new Vector<Morph>();
		for(BoxedMorph boxedMorph: boxedMorphs) {
			morphs.add(boxedMorph.getMorph());
		}
		return morphs;
	}
	
	public boolean isEmpty() {
		return boxedMorphs.isEmpty();
	}
	
	public void add(BoxedMorph boxedMorph) {
		boxedMorphs.add(boxedMorph);
	}
	public void remove(BoxedMorph boxedMorphVictim) {
		if(selectedBoxedMorph == boxedMorphVictim) {
			selectedBoxedMorph = null;
		}
		boxes.removeBox(boxedMorphVictim.getBox());
		boxedMorphs.remove(boxedMorphVictim);
	}
	public void removeAllElements() {
		selectedBoxedMorph = null;
		boxedMorphs.removeAllElements();
	}
	public Iterator<BoxedMorph> iterator() {
		return boxedMorphs.iterator();
	}
	
	public BoxedMorph getBoxedMorph(Rect box) {
		for(BoxedMorph boxedMorph: boxedMorphs) 
			if(boxedMorph.getBox() == box)
				return boxedMorph;
		return null;
	}
	
	public Vector<BoxedMorph> getBoxedMorphs() {
		return boxedMorphs;
	}

	public void setBoxedMorphs(Vector<BoxedMorph> boxedMorphs) {
		this.boxedMorphs = boxedMorphs;
	}
	
	public BoxedMorph findBoxedMorphForMorph(Morph morph) {
	    for(BoxedMorph boxedMorph: boxedMorphs) {
	        if(boxedMorph.getMorph() == morph) {
	            return boxedMorph;
	        }
	    }
	    return null;
	}

	public Vector<BoxedMorph> findBoxedMorphsForMorphAndDescendents(BoxedMorph boxedMorph) {
	    
	    Vector<BoxedMorph> boxedMorphAndDescendents = new Vector<BoxedMorph>();
	    if(boxedMorph != null) {
    	    boxedMorphAndDescendents.add(boxedMorph);
    	    Morph parentMorph = boxedMorph.getMorph();
    	    Pedigree pedigree = parentMorph.getPedigree();
    	    Morph child = pedigree.firstBorn;
    	    while(child != null) {
    	        BoxedMorph childBoxedMorph = findBoxedMorphForMorph(child);
    	        if(childBoxedMorph != null) {
    	            if(child.getPedigree().parent != null) {
    	                boxedMorphAndDescendents.addAll(findBoxedMorphsForMorphAndDescendents(childBoxedMorph));
                        
    	            }
        	        child = childBoxedMorph.getMorph().getPedigree().youngerSib;
    	        } else {
    	            child = null;
    	        }
    	    }
	    }
	    return boxedMorphAndDescendents;
	}
	
}
