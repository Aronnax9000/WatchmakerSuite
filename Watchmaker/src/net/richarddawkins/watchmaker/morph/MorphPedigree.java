package net.richarddawkins.watchmaker.morph;

public class MorphPedigree {
    
    public MorphPedigree(Morph me) {
        this.me = me;
    }
    
    public final Morph me;
    public Morph parent;
    public Morph youngerSib;
    public Morph elderSib;
    public Morph prec;
    public Morph next;
    public Morph firstBorn;
    public Morph lastBorn;
    
    public int getOffspringCount(boolean deep) {
        int count = 0;
        Morph child = firstBorn;
        while (child != null) {
            count++;
            if (deep)
                count += child.getPedigree().getOffspringCount(true);
            child = child.getPedigree().youngerSib;
        }

        return count;
    }

	public void addOffspring(Morph childMorph) {
		MorphPedigree childPedigree = childMorph.getPedigree();
		if(firstBorn == null) {
 			firstBorn = childMorph;
		} else {
			// Real computer scientists, please shoot me now. -- ABC
			Morph youngestOffspring = firstBorn;
			while(youngestOffspring.getPedigree().youngerSib != null) {
				youngestOffspring = youngestOffspring.getPedigree().youngerSib;
			}
			childPedigree.elderSib = youngestOffspring;
			youngestOffspring.getPedigree().youngerSib = childMorph;
			childPedigree.parent = me;
		}
		lastBorn = childMorph;
		
		
		Morph youngestOffspring;
		for(youngestOffspring = firstBorn; 
				youngestOffspring != null; 
				youngestOffspring = youngestOffspring.getPedigree().youngerSib) {
			
		}
		
	}






}
