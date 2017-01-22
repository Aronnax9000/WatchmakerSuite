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






}
