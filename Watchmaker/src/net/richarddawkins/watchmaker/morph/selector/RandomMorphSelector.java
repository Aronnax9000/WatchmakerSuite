package net.richarddawkins.watchmaker.morph.selector;

import java.util.Collection;

import net.richarddawkins.watchmaker.genome.mutation.Random;
import net.richarddawkins.watchmaker.morph.Morph;

public class RandomMorphSelector implements MorphSelector {

    @Override
    public Morph best(Morph target, Collection<Morph> morphs) {
        
        Morph[] morphArray = morphs.toArray(new Morph[0]);
        return morphArray[Random.randInt(morphArray.length) - 1];
    }
}
