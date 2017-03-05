package net.richarddawkins.watchmaker.morph.selector;

import java.util.Collection;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class MorphZillaSelector extends SimpleMorphSelector {
    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.morph.selector.MorphZillaSelector");

  
    
    
    @Override
    public Morph best(Morph target, Collection<Morph> genomes) {
        Morph best = super.best(target, genomes);
        logger.info("Winning Rect:" + best.getPhenotype().getMargin());
        return best;
    }




    @Override
    protected boolean favor(Morph target, Morph potentialBetter,
            Morph candidateToBeat) {
        Phenotype potentialBetterBody = (Phenotype) potentialBetter.getPhenotype();
        int potentialBetterBodyArea =  potentialBetterBody.getMargin().getArea();
        
        
        Phenotype candidateToBeatBody =  (Phenotype) target.getPhenotype();
        int candidateToBeatBodyArea = candidateToBeatBody.getMargin().getArea();
        logger.fine("Betterthan " + potentialBetterBodyArea + " > " + candidateToBeatBodyArea);
        return potentialBetterBodyArea > candidateToBeatBodyArea;
        
    }


}
