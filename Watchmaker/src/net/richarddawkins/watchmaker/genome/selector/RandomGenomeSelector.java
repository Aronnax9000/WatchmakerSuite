package net.richarddawkins.watchmaker.genome.selector;

import java.util.Collection;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.mutation.Random;

public class RandomGenomeSelector implements GenomeSelector {

    @Override
    public Genome best(Genome target, Collection<Genome> genomes) {
        Genome[] genomeArray = genomes.toArray(new Genome[0]);
        return genomeArray[Random.randInt(genomeArray.length) - 1];
    }
    

}
