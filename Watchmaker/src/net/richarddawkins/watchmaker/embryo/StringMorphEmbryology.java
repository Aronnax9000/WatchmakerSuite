package net.richarddawkins.watchmaker.embryo;

import net.richarddawkins.watchmaker.genome.StringGene;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.phenotype.StringPhenotype;

public class StringMorphEmbryology extends SimpleEmbryology {

    @Override
    public void develop(Morph morph) {
        // TODO Auto-generated method stub
        super.develop(morph);
        StringGene gene = (StringGene)morph.getGenome().getGene(0);
        String phenotypeValue = gene.getValue();
        morph.setPhenotype(new StringPhenotype(phenotypeValue));

    }
    

}
