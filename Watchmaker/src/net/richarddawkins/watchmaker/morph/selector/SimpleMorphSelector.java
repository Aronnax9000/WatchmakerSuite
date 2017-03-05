package net.richarddawkins.watchmaker.morph.selector;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.morph.Morph;

abstract public class SimpleMorphSelector implements MorphSelector {
    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.morph.selector.SimpleMorphSelector");

    public SimpleMorphSelector() {
        super();
    }
    @Override
    public Morph best(Morph target, Collection<Morph> genomes) {
        Iterator<Morph> iter = genomes.iterator();
        Morph bestSoFar = iter.next();
        while (iter.hasNext()) {
            Morph candidate = iter.next();
            if (favor(target, candidate, bestSoFar)) {
                bestSoFar = candidate;
                logger.fine("Winning best so far " + bestSoFar);
            }
        }
        logger.fine("Winner " + bestSoFar);
        return bestSoFar;
    }
    
    abstract protected boolean favor(Morph target, Morph potentialBetter,
            Morph candidateToBeat);
}